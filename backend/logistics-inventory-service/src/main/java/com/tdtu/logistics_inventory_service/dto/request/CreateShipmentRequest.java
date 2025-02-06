package com.tdtu.logistics_inventory_service.dto.request;

import com.tdtu.logistics_inventory_service.dto.request.CreateShipmentSegmentRequest;
import com.tdtu.logistics_inventory_service.enumrator.ShipmentMethod;
import com.tdtu.logistics_inventory_service.enumrator.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequest {
	String trackingNumber;
	Long shipper;
	ShipmentMethod shipmentMethod;
	Long fromWarehouseId;
	Long toWarehouseId;
	ShipmentStatus shipmentStatus;
	LocalDateTime departureTime;
	List<String> orders;
	List<CreateShipmentSegmentRequest> shipmentSegmentRequests;
}
