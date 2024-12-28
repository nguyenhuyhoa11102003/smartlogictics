package com.tdtu.logistics_shipments_service.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateShipmentRequest {
	private Long shipperId; // Staff Shipper
	private String shipmentMethod; // Method of shipment (e.g., "road", "air", "sea")
	private Long fromWarehouseId; // ID of the source warehouse
	private Long toWarehouseId; // ID of the destination warehouse
	private List<Long> intermediateWarehouseIds; // List of intermediate warehouse IDs
	private LocalDateTime estimatedDeliveryDate; // Estimated delivery date
}
