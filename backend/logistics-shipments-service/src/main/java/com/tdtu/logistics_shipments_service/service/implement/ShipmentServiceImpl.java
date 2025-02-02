package com.tdtu.logistics_shipments_service.service.implement;

import com.tdtu.logistics_shipments_service.dto.request.ActualDeliveryTimeRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentSegmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.ShipmentStatusUpdateRequest;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.delivery.Route;
import com.tdtu.logistics_shipments_service.enumrator.SegmentStatus;
import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import com.tdtu.logistics_shipments_service.enumrator.TrafficCondition;
import com.tdtu.logistics_shipments_service.enumrator.WeatherCondition;
import com.tdtu.logistics_shipments_service.model.Shipment;
import com.tdtu.logistics_shipments_service.model.ShipmentSegment;
import com.tdtu.logistics_shipments_service.repository.ShipmentRepository;
import com.tdtu.logistics_shipments_service.service.ShipmentService;
import com.tdtu.logistics_shipments_service.service.client.DeliveryServiceFeignClient;
import com.tdtu.logistics_shipments_service.service.client.OrderServiceFeignClient;
import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.service.client.WarehouseServiceFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class ShipmentServiceImpl implements ShipmentService {
	ShipmentRepository shipmentRepository;
	OrderServiceFeignClient orderServiceFeignClient;
	WarehouseServiceFeignClient warehouseServiceFeignClient;
	DeliveryServiceFeignClient deliveryServiceFeignClient;

	@Override
	public void addOrdersToShipment(Long shipmentId, List<String> orderIds) {
		Shipment shipmentEntity = shipmentRepository
				.findById(shipmentId).orElseThrow(() -> new RuntimeException("Shipment not found"));
		for (String orderId : orderIds) {
			ApiResponse<OrderInfResponse> orderResponse = orderServiceFeignClient.getOrderById(orderId);
			if (orderResponse != null && orderResponse.getResult() != null) {
				shipmentEntity.getOrders().add(orderId);
			} else {
				throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId);
			}
		}
		shipmentRepository.save(shipmentEntity);
	}

//	@Transactional
	@Override
	public ShipmentInfResponse createShipment(CreateShipmentRequest requestDTO) {
		List<CreateShipmentSegmentRequest> createShipmentSegmentRequests = requestDTO.getShipmentSegmentRequests();
		// Create shipment entity
		Shipment shipment = createShipmentEntity(requestDTO);
		log.info(" {}: func:{}", "ShipmentServiceImpl", "createShipment");

		// logic for creating shipment segments
		List<Long> warehouseIds = new ArrayList<>();
		warehouseIds.add(requestDTO.getFromWarehouseId());  // diem dau
		warehouseIds.addAll(shipment.getIntermediateWarehouseIds()); // diem giua
		warehouseIds.add(requestDTO.getToWarehouseId()); // diem cuoi

		//  Call warehouse service to get warehouse information
		ApiResponse<List<WarehouseInfResponse>> response = warehouseServiceFeignClient
				.getWarehousesByIds(warehouseIds);
		if (!response.isSuccess()) {
			throw new RuntimeException("Error when calling warehouse service");
		}

		//  Create shipment segments
		List<WarehouseInfResponse> warehouseInfResponses = response.getResult();
		LocalDateTime startTime = shipment.getDepartureTime();
		LocalDateTime arrivalTime = null;
		for (int i = 0; i < createShipmentSegmentRequests.size(); i++) {
			WarehouseInfResponse fromWarehouse = warehouseInfResponses.get(i);
			WarehouseInfResponse toWarehouse = warehouseInfResponses.get(i + 1);
			CreateShipmentSegmentRequest segmentRequest = createShipmentSegmentRequests.get(i);

			// Get route
			ApiResponse<List<Route>> route = getRoutes(fromWarehouse, toWarehouse);
			float summaryDuration = route.getResult().get(0).getSections().get(0).getSummary().getDuration();
			float summaryLength = route.getResult().get(0).getSections().get(0).getSummary().getLength();
			float summaryBaseDuration = route.getResult().get(0).getSections().get(0).getSummary().getBaseDuration();

			// Create shipment segment
			LocalDateTime endTime = startTime.plusSeconds((long) summaryDuration);
			ShipmentSegment shipmentSegment = ShipmentSegment.builder()
					.shipment(shipment)
					.fromWarehouseId(fromWarehouse.getId())
					.toWarehouseId(toWarehouse.getId())
					.departureTime(startTime)
					.arrivalTime(endTime)
					.weatherCondition(segmentRequest.getWeatherCondition())
					.trafficCondition(segmentRequest.getTrafficCondition())
					.segmentStatus(segmentRequest.getSegmentStatus())
					.notes(segmentRequest.getNotes())
					.summaryDuration(summaryDuration)
					.summaryLength(summaryLength)
					.summaryBaseDuration(summaryBaseDuration)
					.stopoverDuration(segmentRequest.getStopoverDuration())
					.build();
			shipment.getShipmentSegments().add(shipmentSegment);
			startTime = endTime.plusMinutes((long) segmentRequest.getStopoverDuration());
			arrivalTime = startTime;
		}
		shipment.setArrivalTime(arrivalTime);
		shipment.setCreateAt(ZonedDateTime.now().toInstant());
		shipment.setUpdateAt(ZonedDateTime.now().toInstant());
		Shipment savedShipment = shipmentRepository.save(shipment);
		return getShipmentById(savedShipment.getId());
	}

	private Shipment createShipmentEntity(CreateShipmentRequest requestDTO) {
		String message = "Create shipment entity successfully";
		List<Long> intermediateWarehouseIds = requestDTO
				.getShipmentSegmentRequests()
				.stream()
				.map(CreateShipmentSegmentRequest::getDestinationWarehouseId)
				.toList();
		Shipment shipment = Shipment.builder()
				.trackingNumber(requestDTO.getTrackingNumber())
				.shipper(requestDTO.getShipper())
				.shipmentMethod(requestDTO.getShipmentMethod())
				.fromWarehouseId(requestDTO.getFromWarehouseId())
				.intermediateWarehouseIds(intermediateWarehouseIds)
				.toWarehouseId(requestDTO.getToWarehouseId())
				.shipmentStatus(requestDTO.getShipmentStatus())
				.departureTime(requestDTO.getDepartureTime())
				.orders(requestDTO.getOrders())
				.shipmentSegments(new ArrayList<>())
				.shipmentStatus(ShipmentStatus.PENDING)
				.build();
		log.info("{}: func:{}  , message:{}", "ShipmentServiceImpl", "createShipmentEntity", message);
		return shipment;
	}

	private ApiResponse<List<Route>> getRoutes(
			WarehouseInfResponse fromWarehouse,
			WarehouseInfResponse toWarehouse) {
		// call api for new route segment
		ApiResponse<List<Route>> route = deliveryServiceFeignClient.getRoutes(
				fromWarehouse.getAddressDetail().getLatitude() + "," + fromWarehouse.getAddressDetail().getLongitude(),
				toWarehouse.getAddressDetail().getLatitude() + "," + toWarehouse.getAddressDetail().getLongitude(),
				"summary",
				"car");
		if (!route.isSuccess()) {
			throw new RuntimeException("Error when calling delivery service");
		}
		return route;
	}

	@Override
	public ShipmentInfResponse getShipmentById(Long id) {
		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Shipment not found"));
		ShipmentInfResponse response = new ShipmentInfResponse();
		response.setId(shipment.getId());
		response.setTrackingNumber(shipment.getTrackingNumber());
		response.setShipper(shipment.getShipper());
		response.setShipmentMethod(shipment.getShipmentMethod());
		response.setFromWarehouseId(shipment.getFromWarehouseId());
		response.setToWarehouseId(shipment.getToWarehouseId());
		response.setIntermediateWarehouseIds(shipment.getIntermediateWarehouseIds());
		response.setShipmentStatus(shipment.getShipmentStatus().name());
		response.setDepartureTime(shipment.getDepartureTime().toString());
		response.setArrivalTime(shipment.getArrivalTime().toString());
		response.setOrders(shipment.getOrders());
		response.setShipmentSegments(new ArrayList<>());
		response.setCreateAt(shipment.getCreateAt().toString());
		response.setUpdateAt(shipment.getUpdateAt().toString());
		return response;
	}

	@Override
	public ShipmentInfResponse updateShipmentStatus(Long id, ShipmentStatusUpdateRequest requestDTO) {
		return null;
	}

	@Override
	public ShipmentInfResponse trackShipmentByTrackingNumber(String trackingNumber) {
		return null;
	}

	@Override
	public List<ShipmentInfResponse> getShipmentsByOrderId(Long orderId) {
		return null;
	}

	@Override
	public List<ShipmentInfResponse> getShipmentsByStatus(ShipmentStatus status) {
		return null;
	}

	@Override
	public void deleteShipment(Long id) {

	}

	@Override
	public void updateActualDeliveryTime(Long id, ActualDeliveryTimeRequest actualDeliveryTime) {

	}
}
