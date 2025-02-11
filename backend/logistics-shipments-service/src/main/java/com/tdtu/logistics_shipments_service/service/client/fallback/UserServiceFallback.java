package com.tdtu.logistics_shipments_service.service.client.fallback;

import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.ShipperInfResponse;
import com.tdtu.logistics_shipments_service.service.client.UserServiceFeignClient;


public class UserServiceFallback implements UserServiceFeignClient {

	@Override
	public ApiResponse<ShipperInfResponse> getShipperById(String id) {
		return null;
	}
}
