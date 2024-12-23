package com.tdtu.logistics_users_service.service;

import com.tdtu.logistics_users_service.dto.request.PickupRequest;
import com.tdtu.logistics_users_service.dto.request.CreateShipperRequest;
import com.tdtu.logistics_users_service.dto.response.ShipperInfResponse;

import java.util.Set;

public interface ShipperService {
	ShipperInfResponse getShipperInfById(String id);

	ShipperInfResponse getShipperInfByStaffId(String staffId);

	ShipperInfResponse createShipper(CreateShipperRequest createShipperRequest);

	Set<ShipperInfResponse> getShipperByWarehouse(String warehouseId);

}
