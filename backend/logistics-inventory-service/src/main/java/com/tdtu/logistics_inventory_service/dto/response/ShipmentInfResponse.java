package com.tdtu.logistics_inventory_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
  	String departureTime;
	String arrivalTime;
	List<String> orders;
	List<ShipmentSegmentInfResponse> shipmentSegments;
	String createAt;
	String updateAt;

}
