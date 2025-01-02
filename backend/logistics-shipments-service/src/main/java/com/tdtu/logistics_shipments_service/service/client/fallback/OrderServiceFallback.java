package com.tdtu.logistics_shipments_service.service.client.fallback;

import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.service.client.OrderServiceFeignClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceFallback implements OrderServiceFeignClient {
	@Override
	public ApiResponse<OrderInfResponse> getOrderById(String orderId) {
		return null;
	}
}
