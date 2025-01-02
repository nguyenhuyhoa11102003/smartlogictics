package com.tdtu.logistics_shipments_service.service.client;


import com.tdtu.logistics_shipments_service.config.feignClient.ClientConfig;
import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_shipments_service.service.client.fallback.OrderServiceFallback;
import com.tdtu.logistics_shipments_service.service.client.fallback.WarehouseServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
		value = "warehouses-service",
		url = "${warehouse-service.url}",
		configuration = ClientConfig.class,
		fallback = WarehouseServiceFallback.class
)
public interface WarehouseServiceFeignClient {
	@GetMapping("/api/v1/warehouses/get-ids")
	ApiResponse<List<WarehouseInfResponse>> getWarehousesByIds(@RequestParam("ids") List<Long> ids);
}
