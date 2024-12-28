package com.tdtu.logistics_shipments_service.service.implement;

import com.tdtu.logistics_shipments_service.dto.request.ActualDeliveryTimeRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.ShipmentStatusUpdateRequest;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import com.tdtu.logistics_shipments_service.model.Shipment;
import com.tdtu.logistics_shipments_service.repository.ShipmentRepository;
import com.tdtu.logistics_shipments_service.service.ShipmentService;
import com.tdtu.logistics_shipments_service.service.client.OrderServiceFeignClient;
import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentServiceImpl implements ShipmentService {

	ShipmentRepository shipmentRepository;
	OrderServiceFeignClient orderServiceFeignClient;

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
		Shipment shipment = new Shipment();
		shipment.setShipmentMethod(requestDTO.getShipmentMethod());
		shipment.setFromWarehouseId(requestDTO.getFromWarehouseId());
		shipment.setToWarehouseId(requestDTO.getToWarehouseId());
		shipment.setIntermediateWarehouseIds(requestDTO.getIntermediateWarehouseIds());
		shipment.setEstimatedDeliveryDate(requestDTO.getEstimatedDeliveryDate());
		shipmentRepository.save(shipment);

		ShipmentInfResponse response = new ShipmentInfResponse();
		response.setId(shipment.getId());
		response.setShipmentMethod(shipment.getShipmentMethod());
		response.setFromWarehouseId(shipment.getFromWarehouseId());
		response.setToWarehouseId(shipment.getToWarehouseId());
		response.setIntermediateWarehouseIds(shipment.getIntermediateWarehouseIds());
		response.setEstimatedDeliveryDate(shipment.getEstimatedDeliveryDate());
		return response;
	}

	@Override
	public ShipmentInfResponse getShipmentById(Long id) {
		return null;
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
