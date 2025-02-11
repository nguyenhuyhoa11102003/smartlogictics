package com.tdtu.logistics_shipments_service.service.client;


import com.tdtu.logistics_shipments_service.config.feignClient.ClientConfig;
import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.ShipperInfResponse;
import com.tdtu.logistics_shipments_service.service.client.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
		value = "users-service",
		url = "${user-service.url}",
		configuration = ClientConfig.class,
		fallback = UserServiceFallback.class
)
public interface UserServiceFeignClient {

	@GetMapping("/shipper/get/{id}")
	ApiResponse<ShipperInfResponse> getShipperById(@PathVariable("id") String id);
}
