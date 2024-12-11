package com.tdtu.logistics_warehouse_service.service;

import com.tdtu.logistics_warehouse_service.dto.request.CreateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.request.UpdateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.response.WarehouseInfResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {

	WarehouseInfResponse createWarehouse(CreateWarehouseRequest createWarehouseRequest);

	Optional<WarehouseInfResponse> getWareHouseById(Long id);

	WarehouseInfResponse updateWarehouse(UpdateWarehouseRequest createWarehouseRequest, Long id);

	WarehouseInfResponse deleteWarehouse(Long id);


	List<WarehouseInfResponse> getAllWarehouses(int pageNo, int pageSize);


}
