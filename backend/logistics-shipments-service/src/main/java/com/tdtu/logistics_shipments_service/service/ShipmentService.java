package com.tdtu.logistics_shipments_service.service;

import com.tdtu.logistics_shipments_service.dto.request.ActualDeliveryTimeRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.ShipmentStatusUpdateRequest;
import com.tdtu.logistics_shipments_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipmentService {
	void addOrdersToShipment(Long shipmentId, List<String> orderIds);

	ShipmentInfResponse createShipment(CreateShipmentRequest requestDTO);

	ShipmentInfResponse getShipmentById(Long id);

	ShipmentInfResponse updateShipmentStatus(Long id, ShipmentStatusUpdateRequest requestDTO);

	ShipmentInfResponse trackShipmentByTrackingNumber(String trackingNumber);

	List<ShipmentInfResponse> getShipmentsByOrderId(Long orderId);

	List<ShipmentInfResponse> getShipmentsByStatus(ShipmentStatus status);

	void deleteShipment(Long id);

	void updateActualDeliveryTime(Long id, ActualDeliveryTimeRequest actualDeliveryTime);

	Page<ShipmentInfResponse> getAllPaginated(Pageable pageable);
}
