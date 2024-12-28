package com.tdtu.logistics_shipments_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ShipmentInfResponse {
	private Long id; // Shipment ID
	private String trackingNumber; // Tracking number
	private Long shipperId; // Associated Shipper ID
	private String shipmentStatus; // Shipment Status
	private String shipmentMethod; // Shipment method (e.g., "road", "air", "sea")
	private Long fromWarehouseId; // Source warehouse ID
	private Long toWarehouseId; // Destination warehouse ID
	private List<Long> intermediateWarehouseIds; // Intermediate warehouses
	private LocalDateTime shipmentStartDate; // Shipment start date
	private LocalDateTime estimatedDeliveryDate; // Estimated delivery date
	private LocalDateTime actualDeliveryDate; // Actual delivery date
}
