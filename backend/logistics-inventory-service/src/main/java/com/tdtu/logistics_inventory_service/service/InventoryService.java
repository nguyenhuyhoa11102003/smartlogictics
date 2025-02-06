package com.tdtu.logistics_inventory_service.service;

import com.tdtu.logistics_inventory_service.dto.request.AddInventoryRequest;
import com.tdtu.logistics_inventory_service.dto.response.InventoryResponse;

public interface InventoryService {

	InventoryResponse addInventory(AddInventoryRequest request);
}

