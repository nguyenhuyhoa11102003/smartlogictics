package com.tdtu.logistics_shipments_service.service.implement;

import com.google.gson.Gson;
import com.tdtu.logistics_shipments_service.dto.request.ActualDeliveryTimeRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentSegmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.ShipmentStatusUpdateRequest;
import com.tdtu.logistics_shipments_service.dto.response.*;
import com.tdtu.logistics_shipments_service.dto.response.delivery.Route;
import com.tdtu.logistics_shipments_service.enumrator.*;
import com.tdtu.logistics_shipments_service.model.Shipment;
import com.tdtu.logistics_shipments_service.model.ShipmentSegment;
import com.tdtu.logistics_shipments_service.repository.ShipmentRepository;
import com.tdtu.logistics_shipments_service.service.ShipmentService;
import com.tdtu.logistics_shipments_service.service.client.DeliveryServiceFeignClient;
import com.tdtu.logistics_shipments_service.service.client.OrderServiceFeignClient;
import com.tdtu.logistics_shipments_service.service.client.UserServiceFeignClient;
import com.tdtu.logistics_shipments_service.service.client.WarehouseServiceFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
	UserServiceFeignClient userServiceFeignClient;

	// Add orders to shipment
	@Override
	public void addOrdersToShipment(Long shipmentId, List<String> orderIds) {
		if (orderIds == null || orderIds.isEmpty()) {
			throw new RuntimeException("Danh sách đơn hàng trống");
		}
		if (shipmentId == null) {
			throw new RuntimeException("ID lô hàng không được để trống");
		}
		Shipment shipmentEntity = getShipmentEntityById(shipmentId);
		// Get info of orders
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

	// Create shipment
	@Transactional
	@Override
	public ShipmentInfResponse createShipment(CreateShipmentRequest requestDTO) {

		List<CreateShipmentSegmentRequest> createShipmentSegmentRequests = requestDTO.getShipmentSegmentRequests();

		createShipmentSegmentRequests.add(CreateShipmentSegmentRequest.builder()
						.weatherCondition(WeatherCondition.CLEAR)
						.trafficCondition(TrafficCondition.LIGHT)
						.segmentStatus(SegmentStatus.NOT_STARTED)
						.notes("Giao hàng")
						.destinationWarehouseId(requestDTO.getToWarehouseId())
						.stopoverDuration(0)
				.build());

		// Create shipment entity
		Shipment shipment = createShipmentEntity(requestDTO);
		log.info(" {}: func:{}", "ShipmentServiceImpl", "createShipment");

		// logic for creating shipment segments
		List<Long> warehouseIds = new ArrayList<>();
		warehouseIds.add(requestDTO.getFromWarehouseId());  // diem dau
		warehouseIds.addAll(shipment.getIntermediateWarehouseIds()); // diem giua
		warehouseIds.add(requestDTO.getToWarehouseId()); // diem cuoi

		//  Call warehouse service to get warehouse information
		ApiResponse<List<WarehouseInfResponse>> response = warehouseServiceFeignClient.getWarehousesByIds(warehouseIds);
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
			ShipmentSegment shipmentSegment = ShipmentSegment.builder().shipment(shipment).fromWarehouseId(fromWarehouse.getId()).toWarehouseId(toWarehouse.getId()).departureTime(startTime).arrivalTime(endTime).weatherCondition(WeatherCondition.CLEAR).trafficCondition(TrafficCondition.LIGHT).segmentStatus(SegmentStatus.NOT_STARTED).notes(segmentRequest.getNotes()).summaryDuration(summaryDuration).summaryLength(summaryLength).summaryBaseDuration(summaryBaseDuration).stopoverDuration(segmentRequest.getStopoverDuration()).build();

			shipment.getShipmentSegments().add(shipmentSegment);
			startTime = endTime.plusMinutes((long) segmentRequest.getStopoverDuration());
			arrivalTime = startTime;
		}
		shipment.setArrivalTime(arrivalTime);
		Shipment savedShipment = shipmentRepository.save(shipment);
		return getShipmentById(savedShipment.getId());
	}

	// Create shipment entity
	private Shipment createShipmentEntity(CreateShipmentRequest requestDTO) {
		String message = "Create shipment entity successfully";
		List<Long> intermediateWarehouseIds = requestDTO.getShipmentSegmentRequests().stream().map(CreateShipmentSegmentRequest::getDestinationWarehouseId).toList();

		// handle add capacity
		if (requestDTO.getShipmentMethod() == ShipmentMethod.XE_TAI) {
			requestDTO.setCapacity(1000.0);
		} else if (requestDTO.getShipmentMethod() == ShipmentMethod.XE_MAY) {
			requestDTO.setCapacity(100.0);
		}

		Shipment shipment = Shipment.builder().trackingNumber(requestDTO.getTrackingNumber()).shipper(requestDTO.getShipper()).shipmentMethod(requestDTO.getShipmentMethod()).fromWarehouseId(requestDTO.getFromWarehouseId()).intermediateWarehouseIds(intermediateWarehouseIds).toWarehouseId(requestDTO.getToWarehouseId()).shipmentStatus(requestDTO.getShipmentStatus()).departureTime(requestDTO.getDepartureTime()).orders(requestDTO.getOrders()).shipmentSegments(new ArrayList<>()).shipmentStatus(ShipmentStatus.PENDING).shipmentType(ShipmentType.ECONOMY).totalWeight(0.0).capacity(requestDTO.getCapacity()).build();

		log.info("{}: func:{}  , message:{}", "ShipmentServiceImpl", "createShipmentEntity", message);
		return shipment;
	}

	// Call delivery service to get route
	private ApiResponse<List<Route>> getRoutes(WarehouseInfResponse fromWarehouse, WarehouseInfResponse toWarehouse) {
		// call api for new route segment
		ApiResponse<List<Route>> route = deliveryServiceFeignClient.getRoutes(fromWarehouse.getAddressDetail().getLatitude() + "," + fromWarehouse.getAddressDetail().getLongitude(), toWarehouse.getAddressDetail().getLatitude() + "," + toWarehouse.getAddressDetail().getLongitude(), "summary", "car");
		if (!route.isSuccess()) {
			throw new RuntimeException("Error when calling delivery service");
		}
		return route;
	}


	@Override
	public ShipmentInfResponse getShipmentById(Long id) {
		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Shipment not found"));
		ShipmentInfResponse response = mapShipmentToResponse(shipment);
		List<ShipmentSegmentInfResponse> shipmentSegmentInfResponses = mapShipmentSegmentsToResponse(shipment);
		response.setShipmentSegments(shipmentSegmentInfResponses);
		return response;
	}

	// map shipment to response
	private ShipmentInfResponse mapShipmentToResponse(Shipment shipment) {
		ShipmentInfResponse response = new ShipmentInfResponse();
		response.setId(shipment.getId());
		response.setTrackingNumber(shipment.getTrackingNumber());
		response.setShipper(shipment.getShipper());
		response.setShipmentMethod(shipment.getShipmentMethod().toString());
		response.setFromWarehouseId(shipment.getFromWarehouseId());
		response.setToWarehouseId(shipment.getToWarehouseId());
		response.setIntermediateWarehouseIds(shipment.getIntermediateWarehouseIds());
		response.setShipmentStatus(shipment.getShipmentStatus().name());
		response.setDepartureTime(shipment.getDepartureTime().toString());
		response.setArrivalTime(shipment.getArrivalTime().toString());
		response.setOrders(shipment.getOrders());
		response.setCreateAt(shipment.getCreateAt().toString());
		response.setUpdateAt(shipment.getUpdateAt().toString());
		response.setShipmentSegments(mapShipmentSegmentsToResponse(shipment));

		ApiResponse<WarehouseInfResponse> fromWarehouse = warehouseServiceFeignClient.getWarehouseById(shipment.getFromWarehouseId());
		ApiResponse<WarehouseInfResponse> toWarehouse = warehouseServiceFeignClient.getWarehouseById(shipment.getToWarehouseId());
		if (fromWarehouse.isSuccess() && toWarehouse.isSuccess()) {
			response.setFromWarehouse(fromWarehouse.getResult());
			response.setToWarehouse(toWarehouse.getResult());
		}
		ApiResponse<List<WarehouseInfResponse>> intermediateWarehouses = warehouseServiceFeignClient.getWarehousesByIds(shipment.getIntermediateWarehouseIds());
		if (intermediateWarehouses.isSuccess()) {
			response.setIntermediateWarehouses(intermediateWarehouses.getResult());
		}
		return response;
	}


	// map shipment segments to response
	private List<ShipmentSegmentInfResponse> mapShipmentSegmentsToResponse(Shipment shipment) {
		List<ShipmentSegment> shipmentSegments  =  shipment.getShipmentSegments();
		List<ShipmentSegmentInfResponse> shipmentSegmentInfResponses = new ArrayList<>();
		shipmentSegments.forEach(t -> {
			ShipmentSegmentInfResponse shipmentInfResponse = new ShipmentSegmentInfResponse();
			shipmentInfResponse.setId(t.getId());
			shipmentInfResponse.setFromWarehouseId(t.getFromWarehouseId());
			shipmentInfResponse.setToWarehouseId(t.getToWarehouseId());
			shipmentInfResponse.setDepartureTime(t.getDepartureTime());
			shipmentInfResponse.setArrivalTime(t.getArrivalTime());
			shipmentInfResponse.setStopoverDuration(t.getStopoverDuration());
			shipmentInfResponse.setWeatherCondition(t.getWeatherCondition());
			shipmentInfResponse.setTrafficCondition(t.getTrafficCondition());
			shipmentInfResponse.setSegmentStatus(t.getSegmentStatus());
			shipmentInfResponse.setNotes(t.getNotes());
			shipmentInfResponse.setSummaryDuration(t.getSummaryDuration());
			shipmentInfResponse.setSummaryLength(t.getSummaryLength());
			shipmentInfResponse.setSummaryBaseDuration(t.getSummaryBaseDuration());
			shipmentInfResponse.setHoliday(t.isHoliday());

			ApiResponse<WarehouseInfResponse> fromWarehouse = warehouseServiceFeignClient.getWarehouseById(t.getFromWarehouseId());
			ApiResponse<WarehouseInfResponse> toWarehouse = warehouseServiceFeignClient.getWarehouseById(t.getToWarehouseId());
			if (fromWarehouse.isSuccess() && toWarehouse.isSuccess()) {
				shipmentInfResponse.setFromWarehouse(fromWarehouse.getResult());
				shipmentInfResponse.setToWarehouse(toWarehouse.getResult());
			}

			shipmentSegmentInfResponses.add(shipmentInfResponse);
		});
		return shipmentSegmentInfResponses;
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

	@Override
	public Page<ShipmentInfResponse> getAllPaginated(Pageable pageable) {
		return shipmentRepository.findAll(pageable).map(this::mapShipmentToResponse);
	}


	// Get shipment entity by id
	private Shipment getShipmentEntityById(Long id) {
		return shipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Shipment not found"));
	}

}
