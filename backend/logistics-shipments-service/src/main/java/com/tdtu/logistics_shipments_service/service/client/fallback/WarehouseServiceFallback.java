package com.tdtu.logistics_shipments_service.service.client.fallback;

import com.tdtu.logistics_shipments_service.dto.response.ApiResponse;
import com.tdtu.logistics_shipments_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_shipments_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_shipments_service.service.client.OrderServiceFeignClient;
import com.tdtu.logistics_shipments_service.service.client.WarehouseServiceFeignClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class WarehouseServiceFallback implements WarehouseServiceFeignClient {
	@Override
	public ApiResponse<List<WarehouseInfResponse>> getWarehousesByIds(List<Long> ids) {
		return null;
	}
}
