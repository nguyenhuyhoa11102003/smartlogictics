package com.tdtu.logistics_warehouse_service.service;

import com.tdtu.logistics_warehouse_service.dto.request.CreateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.request.UpdateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.response.WarehouseInfResponse;

import java.util.Optional;

public interface WarehouseService {

	WarehouseInfResponse createWarehouse(CreateWarehouseRequest createWarehouseRequest);

	Optional<WarehouseInfResponse> getWareHouseById(Long id);

	WarehouseInfResponse updateWarehouse(UpdateWarehouseRequest createWarehouseRequest, Long id);

	WarehouseInfResponse deleteWarehouse(Long id);


}
