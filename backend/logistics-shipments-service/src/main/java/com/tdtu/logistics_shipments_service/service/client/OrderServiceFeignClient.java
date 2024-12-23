package com.tdtu.logistics_shipments_service.service.client;


import com.tdtu.logistics_shipments_service.config.feignClient.ClientConfig;
import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.service.client.fallback.OrderServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
		value = "users-service",
		url = "${user-service.url}",
		configuration = ClientConfig.class,
		fallback = OrderServiceFallback.class
)
public interface OrderServiceFeignClient {
	@GetMapping("/orders/{id}")
	ApiResponse<OrderInfResponse> getOrderById(@PathVariable("id") String orderId);
}
