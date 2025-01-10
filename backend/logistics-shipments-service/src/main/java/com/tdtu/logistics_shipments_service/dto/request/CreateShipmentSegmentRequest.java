package com.tdtu.logistics_shipments_service.dto.request;


import com.tdtu.logistics_shipments_service.enumrator.SegmentStatus;
import com.tdtu.logistics_shipments_service.enumrator.TrafficCondition;
import com.tdtu.logistics_shipments_service.enumrator.WeatherCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateShipmentSegmentRequest {
	WeatherCondition weatherCondition;
	TrafficCondition trafficCondition;
	SegmentStatus segmentStatus;
	String notes;
	Long destinationWarehouseId;
	float stopoverDuration;
}
