package com.tdtu.logistics_inventory_service.controller;

import com.tdtu.logistics_inventory_service.dto.request.AddInventoryRequest;
import com.tdtu.logistics_inventory_service.dto.response.ApiResponse;
import com.tdtu.logistics_inventory_service.dto.response.InventoryResponse;
import com.tdtu.logistics_inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {

	InventoryService inventoryService;

	@PostMapping("/add")
	public ApiResponse<InventoryResponse> addInventory(@Valid @RequestBody AddInventoryRequest request) {
		InventoryResponse response = inventoryService.addInventory(request);
		return ApiResponse.<InventoryResponse>builder().result(response).build();
	}



}
