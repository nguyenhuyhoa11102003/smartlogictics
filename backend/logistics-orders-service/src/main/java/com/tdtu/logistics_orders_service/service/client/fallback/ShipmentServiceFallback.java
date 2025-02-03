package com.tdtu.logistics_orders_service.service.client.fallback;

import com.tdtu.common.user_service.dto.AddressInfResponse;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.common.user_service.dto.ReceiverInfResponse;
import com.tdtu.common.user_service.dto.ShipperInfResponse;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.dto.response.ShipmentInfResponse;
import com.tdtu.logistics_orders_service.service.client.ShipmentServiceClient;
import com.tdtu.logistics_orders_service.service.client.UserServiceClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ShipmentServiceFallback implements ShipmentServiceClient {


	@Override
	public ApiResponse<Void> addOrdersToShipment(String shipmentId, List<String> orderId) {
		return null;
	}

	@Override
	public ApiResponse<ShipmentInfResponse> getShipmentDetail(String shipmentId) {
		return null;
	}
}

