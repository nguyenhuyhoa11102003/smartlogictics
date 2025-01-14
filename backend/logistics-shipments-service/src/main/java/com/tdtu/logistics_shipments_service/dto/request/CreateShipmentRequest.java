package com.tdtu.logistics_shipments_service.dto.request;

import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequest {
	String trackingNumber;
	Long shipper;
	String shipmentMethod;
	Long fromWarehouseId;
	Long toWarehouseId;
	ShipmentStatus shipmentStatus;
	String departureTime;
	List<String> orders;
	List<CreateShipmentSegmentRequest> shipmentSegmentRequests;
}
