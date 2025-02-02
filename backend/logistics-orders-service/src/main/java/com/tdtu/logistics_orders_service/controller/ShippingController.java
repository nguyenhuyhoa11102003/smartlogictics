package com.tdtu.logistics_orders_service.controller;

import com.tdtu.logistics_orders_service.dto.model.ShippingRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

	private final ShippingService shippingCostService;

	@Autowired
	public ShippingController(ShippingService shippingCostService) {
		this.shippingCostService = shippingCostService;
	}

	// post:  calculate shipping cost for my system
	@PostMapping("/calculate")
	public ApiResponse<BigDecimal> calculateShippingCost(@RequestBody ShippingRequestDTO requestDTO) {
		BigDecimal shippingCost = shippingCostService.calculateShippingCost(requestDTO);
		return ApiResponse.<BigDecimal>builder()
				.code(200)
				.message("Calculate shipping cost successfully")
				.result(shippingCost)
				.build();
	}
}