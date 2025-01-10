package com.tdtu.logistics_shipments_service.service.client.fallback;

import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.delivery.Route;
import com.tdtu.logistics_shipments_service.service.client.DeliveryServiceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DeliveryServiceFallback implements DeliveryServiceFeignClient {

	@Override
	public ApiResponse<List<Route>> getRoutes(String origin, String destination, String returnSummary, String transportMode) {
		log.error("Failed to call getRoutes API in DeliveryService. Falling back to default response.");

		return ApiResponse.<List<Route>>builder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.isSuccess(Boolean.FALSE)
				.message("Fallback: Unable to fetch routes. Please try again later.")
				.result(Collections.emptyList())
				.build();
	}
}
