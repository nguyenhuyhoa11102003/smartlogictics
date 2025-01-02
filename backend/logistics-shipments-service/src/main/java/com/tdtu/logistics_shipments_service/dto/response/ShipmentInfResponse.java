package com.tdtu.logistics_shipments_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ShipmentInfResponse {
	private Long id;
	private String trackingNumber;
	private Long shipperId;
	private String shipmentStatus;
	private String shipmentMethod;
	private Long fromWarehouseId;
	private Long toWarehouseId;
	private List<Long> intermediateWarehouseIds;
	private String shipmentStartDate;
	private String estimatedDeliveryDate;
	private String actualDeliveryDate;
}
