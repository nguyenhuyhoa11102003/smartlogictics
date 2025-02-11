package com.tdtu.logistics_shipments_service.dto.response;

import com.tdtu.logistics_shipments_service.enumrator.SegmentStatus;
import com.tdtu.logistics_shipments_service.enumrator.TrafficCondition;
import com.tdtu.logistics_shipments_service.enumrator.WeatherCondition;
import com.tdtu.logistics_shipments_service.model.Shipment;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentSegmentInfResponse {
	Long id;
	Long fromWarehouseId;
	Long toWarehouseId;
	LocalDateTime departureTime;
	LocalDateTime arrivalTime;
	float stopoverDuration;
	WeatherCondition weatherCondition = WeatherCondition.CLEAR;
	TrafficCondition trafficCondition = TrafficCondition.LIGHT;
	SegmentStatus segmentStatus = SegmentStatus.NOT_STARTED;
	String notes;
	float summaryDuration;
	float summaryLength;
	float summaryBaseDuration;
	boolean isHoliday;

	WarehouseInfResponse fromWarehouse;
	WarehouseInfResponse toWarehouse;

}

