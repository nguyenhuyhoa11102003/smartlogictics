package com.tdtu.logistics_orders_service.service.client;


import com.tdtu.common.user_service.dto.ReceiverInfResponse;
import com.tdtu.logistics_orders_service.configuration.feignClient.ClientConfig;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_orders_service.service.client.fallback.ShipmentServiceFallback;
import com.tdtu.logistics_orders_service.service.client.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
		value = "shipment-service",
		url = "${shipment-service.url}",
		configuration = ClientConfig.class,
		fallback = ShipmentServiceFallback.class
)
public interface ShipmentServiceClient {
	@PostMapping(value = "/shipments/add-orders")
	ApiResponse<Void> addOrdersToShipment(
			@PathVariable String shipmentId,
			@PathVariable List<String> orderId);


	@GetMapping(value = "/shipments/{shipmentId}")
	ApiResponse<ShipmentInfResponse> getShipmentDetail(@PathVariable String shipmentId);
}
