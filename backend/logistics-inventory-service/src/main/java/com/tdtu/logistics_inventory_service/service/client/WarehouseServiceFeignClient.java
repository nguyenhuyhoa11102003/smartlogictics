package com.tdtu.logistics_inventory_service.service.client;


import com.tdtu.logistics_inventory_service.config.feignClient.ClientConfig;
import com.tdtu.logistics_inventory_service.dto.response.ApiResponse;
import com.tdtu.logistics_inventory_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_inventory_service.service.client.fallback.WarehouseServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
		value = "warehouses",
		url = "${warehouse-service.url}/api/v1/warehouses",
		configuration = ClientConfig.class,
		fallback = WarehouseServiceFallback.class
)
public interface WarehouseServiceFeignClient {
	@GetMapping("/get-ids")
	ApiResponse<List<WarehouseInfResponse>> getWarehousesByIds(@RequestParam("ids") List<Long> ids);

	@GetMapping("/{id}")
	ApiResponse<WarehouseInfResponse> getWarehouseById(@PathVariable("id") Long id);

	@PostMapping("/{id}/update-capacity")
	ApiResponse<WarehouseInfResponse> updateCapacity(@PathVariable("id") Long id,
	                                                 @RequestParam("volume") double volume,
	                                                 @RequestParam("weight") double weight);
}

