package com.tdtu.logistics_warehouse_service.service.client;


import com.tdtu.logistics_warehouse_service.config.feignClient.ClientConfig;
import com.tdtu.logistics_warehouse_service.dto.response.ApiResponse;
import com.tdtu.logistics_warehouse_service.dto.response.CoordinatesResponse;
import com.tdtu.logistics_warehouse_service.service.client.fallback.DeliveryServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
		value = "delivery-service",
		url = "${delivery-service.url}",
		configuration = ClientConfig.class,
		fallback = DeliveryServiceFallback.class
)
public interface DeliveryServiceFeignClient {
	@GetMapping("/get_coordinates")
	ApiResponse<CoordinatesResponse> get_coordinates(@RequestParam("address") String address);
}
