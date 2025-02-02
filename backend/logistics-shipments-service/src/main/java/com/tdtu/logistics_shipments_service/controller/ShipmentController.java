package com.tdtu.logistics_shipments_service.controller;

import com.tdtu.logistics_shipments_service.dto.request.ActualDeliveryTimeRequest;
import com.tdtu.logistics_shipments_service.dto.request.AddOrdersToShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.CreateShipmentRequest;
import com.tdtu.logistics_shipments_service.dto.request.ShipmentStatusUpdateRequest;
import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import com.tdtu.logistics_shipments_service.service.ShipmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shipments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentController {

	ShipmentService shipmentService;

	@PostMapping("/create")
	public ApiResponse<ShipmentInfResponse> createShipment(@RequestBody CreateShipmentRequest request) {
		ShipmentInfResponse response = shipmentService.createShipment(request);
		return ApiResponse.<ShipmentInfResponse>builder()
				.code(HttpStatus.CREATED.value())
				.message("Shipment created successfully")
				.result(response)
				.build();
	}

	@PostMapping("/add-orders")
	public ApiResponse<?> addOrdersToShipment(@RequestBody AddOrdersToShipmentRequest request) {
		shipmentService.addOrdersToShipment(request.getShipmentId(), request.getOrderIds());
		Map<String, Object> response = new HashMap<>();
		response.put("shipmentId", request.getShipmentId());
		response.put("status", "UPDATED");
		response.put("message", "Đơn hàng đã được thêm vào lô hàng thành công");
		return ApiResponse.builder()
				.code(HttpStatus.OK.value())
				.message("Orders added to shipment successfully")
				.result(response)
				.build();
	}

	@GetMapping("/{shipmentId}")
	public ApiResponse<ShipmentInfResponse> getShipment(@PathVariable Long shipmentId) {
		ShipmentInfResponse shipment = shipmentService.getShipmentById(shipmentId);
		return ApiResponse.<ShipmentInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Shipment details fetched successfully")
				.result(shipment)
				.build();
	}

	@PutMapping("/{shipmentId}/status")
	public ApiResponse<ShipmentInfResponse> updateShipmentStatus(
			@PathVariable Long shipmentId,
			@RequestBody ShipmentStatusUpdateRequest statusUpdateRequest) {

		ShipmentInfResponse response = shipmentService.updateShipmentStatus(shipmentId, statusUpdateRequest);
		return ApiResponse.<ShipmentInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Shipment status updated successfully")
				.result(response)
				.build();
	}

	@GetMapping("/track/{trackingNumber}")
	public ApiResponse<ShipmentInfResponse> trackShipment(@PathVariable String trackingNumber) {
		ShipmentInfResponse response = shipmentService.trackShipmentByTrackingNumber(trackingNumber);
		return ApiResponse.<ShipmentInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Shipment tracking details fetched successfully")
				.result(response)
				.build();
	}

	@GetMapping("/orders/{orderId}/shipments")
	public ApiResponse<List<ShipmentInfResponse>> getShipmentsByOrder(@PathVariable Long orderId) {
		List<ShipmentInfResponse> responses = shipmentService.getShipmentsByOrderId(orderId);
		return ApiResponse.<List<ShipmentInfResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Shipments for order fetched successfully")
				.result(responses)
				.build();
	}

	@GetMapping("/status/{status}")
	public ApiResponse<List<ShipmentInfResponse>> getShipmentsByStatus(@PathVariable ShipmentStatus status) {
		List<ShipmentInfResponse> responses = shipmentService.getShipmentsByStatus(status);

		return ApiResponse.<List<ShipmentInfResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Shipments fetched by status successfully")
				.result(responses)
				.build();
	}

	@DeleteMapping("/{shipmentId}")
	public ApiResponse<Void> deleteShipment(@PathVariable Long shipmentId) {
		shipmentService.deleteShipment(shipmentId);
		return ApiResponse.<Void>builder()
				.code(HttpStatus.NO_CONTENT.value())
				.message("Shipment deleted successfully")
				.build();
	}

	@PutMapping("/{shipmentId}/actual-delivery")
	public ApiResponse<ShipmentInfResponse> updateActualDeliveryTime(
			@PathVariable Long shipmentId,
			@RequestBody ActualDeliveryTimeRequest actualDeliveryTimeRequest) {
		shipmentService.updateActualDeliveryTime(shipmentId, actualDeliveryTimeRequest);
		ShipmentInfResponse response = shipmentService.getShipmentById(shipmentId);
		return ApiResponse.<ShipmentInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Actual delivery time updated successfully")
				.result(response)
				.build();
	}

}
