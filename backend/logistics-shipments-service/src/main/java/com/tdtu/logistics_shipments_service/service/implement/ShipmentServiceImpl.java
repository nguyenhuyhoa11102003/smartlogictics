package com.tdtu.logistics_shipments_service.service.implement;

import com.tdtu.logistics_shipments_service.dto.request.ActualDeliveryTimeRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentSegmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.ShipmentStatusUpdateRequest;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.WarehouseInfResponse;
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

	@Override
	public ShipmentInfResponse createShipment(CreateShipmentRequest requestDTO) {

		List<CreateShipmentSegmentRequest> createShipmentSegmentRequests = requestDTO.getShipmentSegmentRequests();

		Shipment shipment = Shipment.builder()
				.trackingNumber(requestDTO.getTrackingNumber())
				.shipper(requestDTO.getShipper())
				.shipmentMethod(requestDTO.getShipmentMethod())
				.fromWarehouseId(requestDTO.getFromWarehouseId())
				.intermediateWarehouseIds(requestDTO.getIntermediateWarehouseIds())
				.toWarehouseId(requestDTO.getToWarehouseId())
				.shipmentStatus(requestDTO.getShipmentStatus())
				.build();
		try {
			shipment.setShipmentStartDate(ZonedDateTime.parse(requestDTO.getShipmentStartDate()).toLocalDateTime());
			shipment.setEstimatedDeliveryDate(ZonedDateTime.parse(requestDTO.getShipmentStartDate()).toLocalDateTime());
			shipment.setActualDeliveryDate(ZonedDateTime.parse(requestDTO.getShipmentStartDate()).toLocalDateTime());
		} catch (DateTimeParseException e) {
			throw new RuntimeException("Invalid date format in request", e);
		}
		shipment.setOrders(requestDTO.getOrders());
		shipment.setShipmentSegments(new ArrayList<>());
		shipmentRepository.save(shipment);

		List<Long> warehouseIds = new ArrayList<>();
		warehouseIds.add(requestDTO.getFromWarehouseId());
		warehouseIds.addAll(requestDTO.getIntermediateWarehouseIds());
		warehouseIds.add(requestDTO.getToWarehouseId());

		List<WarehouseInfResponse> warehouseInfResponses = warehouseServiceFeignClient
				.getWarehousesByIds(warehouseIds)
				.getResult();

		// Validate shipment segments
		if (createShipmentSegmentRequests.size() != warehouseIds.size() - 1) {
			throw new RuntimeException("Size of shipment segments and warehouseIds do not match");
		}

		for (int i = 0; i < createShipmentSegmentRequests.size(); i++) {
			WarehouseInfResponse fromWarehouse = warehouseInfResponses.get(i);
			WarehouseInfResponse toWarehouse = warehouseInfResponses.get(i + 1);

			CreateShipmentSegmentRequest segmentRequest = createShipmentSegmentRequests.get(i);

//			ResponseEntity<?> responseEntity = deliveryServiceFeignClient.getRoutes(
//					fromWarehouse.getAddress().getLatitude() + "," + fromWarehouse.getAddress().getLongitude(),
//					toWarehouse.getAddress().getLatitude() + "," + toWarehouse.getAddress().getLongitude(),
//					"summary",
//					"car"
//			);

			ShipmentSegment shipmentSegment = ShipmentSegment.builder()
					.shipment(shipment)
					.fromWarehouseId(fromWarehouse.getId())
					.toWarehouseId(toWarehouse.getId())
					.plannedDuration(segmentRequest.getPlannedDuration())
					.actualDuration(segmentRequest.getActualDuration())
					.plannedStopoverDuration(segmentRequest.getPlannedStopoverDuration())
					.actualStopoverDuration(segmentRequest.getActualStopoverDuration())
					.weatherCondition(segmentRequest.getWeatherCondition())
					.trafficCondition(segmentRequest.getTrafficCondition())
					.segmentStatus(segmentRequest.getSegmentStatus())
					.notes(segmentRequest.getNotes())
					.build();

			shipment.getShipmentSegments().add(shipmentSegment);
		}
		Shipment createdShipment = this.shipmentRepository.saveAndFlush(shipment);
		return getShipmentById(createdShipment.getId());
	}

	@Override
	public ShipmentInfResponse getShipmentById(Long id) {
		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Shipment not found"));
		ShipmentInfResponse response = new ShipmentInfResponse();
		response.setId(shipment.getId());
		response.setShipmentMethod(shipment.getShipmentMethod());
		response.setFromWarehouseId(shipment.getFromWarehouseId());
		response.setToWarehouseId(shipment.getToWarehouseId());
		response.setEstimatedDeliveryDate(String.valueOf(shipment.getEstimatedDeliveryDate()));
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
