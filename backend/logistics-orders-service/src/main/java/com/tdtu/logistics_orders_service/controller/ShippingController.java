package com.tdtu.logistics_orders_service.controller;

import com.tdtu.logistics_orders_service.dto.model.ShippingRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.LocationRequest;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

	private final ShippingService shippingService;


	@Autowired
	public ShippingController(ShippingService shippingService) {
		this.shippingService = shippingService;
	}

	// post:  calculate shipping cost for my system
	@PostMapping("/calculate")
	public ApiResponse<BigDecimal> calculateShippingCost(@RequestBody ShippingRequestDTO requestDTO) {
		BigDecimal shippingCost = shippingService.calculateShippingCost(requestDTO);
		return ApiResponse.<BigDecimal>builder()
				.code(200)
				.message("Calculate shipping cost successfully")
				.result(shippingCost)
				.build();
	}


	@PostMapping("/determine-zone")
	public ApiResponse<?> determineShippingZone(@RequestBody LocationRequest request) {
		double distance = shippingService.calculateDistance(
				request.getPickupLatitude(), request.getPickupLongitude(),
				request.getDeliveryLatitude(), request.getDeliveryLongitude()
		);

		String shippingZone;
		if (distance < 50) {
			shippingZone = "NOI_TINH";
		} else if (distance < 200) {
			shippingZone = "CAN_TINH";
		} else {
			shippingZone = "LIEN_TINH";
		}

		return ApiResponse.builder()
				.code(200)
				.message("Determine shipping zone successfully")
				.result(shippingZone)
				.build();
	}


}