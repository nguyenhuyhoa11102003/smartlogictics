package com.tdtu.logistics_warehouse_service.service.client.fallback;

import com.tdtu.logistics_warehouse_service.dto.response.ApiResponse;
import com.tdtu.logistics_warehouse_service.dto.response.CoordinatesResponse;
import com.tdtu.logistics_warehouse_service.service.client.DeliveryServiceFeignClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeliveryServiceFallback implements DeliveryServiceFeignClient {

	@Override
	public ApiResponse<CoordinatesResponse> get_coordinates(String address) {
		log.error("Fallback invoked for get_coordinates with address: {}", address);
		ApiResponse<CoordinatesResponse> fallbackResponse = new ApiResponse<>();
		fallbackResponse.setCode(500);
		fallbackResponse.setSuccess(false);
		fallbackResponse.setMessage("Service unavailable. Please try again later.");
		fallbackResponse.setResult(null);
		return fallbackResponse;
	}
}
