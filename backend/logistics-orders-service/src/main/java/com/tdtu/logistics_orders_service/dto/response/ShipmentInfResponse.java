package com.tdtu.logistics_orders_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipmentInfResponse {
	Long id;
	String trackingNumber;
	Long shipper;
	String shipmentStatus;
	String shipmentMethod;
	Long fromWarehouseId;
	Long toWarehouseId;
	List<Long> intermediateWarehouseIds;
	String shipmentStartDate;
	String departureTime;
	String arrivalTime;
	List<String> orders;
	List<ShipmentSegmentInfResponse> shipmentSegments;
	String createAt;
	String updateAt;

}

