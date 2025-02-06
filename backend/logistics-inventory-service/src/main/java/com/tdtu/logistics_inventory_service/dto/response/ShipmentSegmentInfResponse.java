package com.tdtu.logistics_inventory_service.dto.response;

import com.tdtu.logistics_inventory_service.enumrator.SegmentStatus;
import com.tdtu.logistics_inventory_service.enumrator.TrafficCondition;
import com.tdtu.logistics_inventory_service.enumrator.WeatherCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

