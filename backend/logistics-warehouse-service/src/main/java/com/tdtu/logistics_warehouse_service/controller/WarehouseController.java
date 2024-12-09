package com.tdtu.logistics_warehouse_service.controller;


import com.tdtu.logistics_warehouse_service.dto.request.CreateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.request.UpdateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.response.ApiResponse;
import com.tdtu.logistics_warehouse_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_warehouse_service.exception.wrapper.NotFoundException;
import com.tdtu.logistics_warehouse_service.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WarehouseController {
	WarehouseService warehouseService;

	@PostMapping(
			value = "/create",
			consumes = "application/json",
			produces = "application/json")
	public ApiResponse<WarehouseInfResponse> createWarehouse(
			@Valid @RequestBody CreateWarehouseRequest createWarehouseRequest) {
		WarehouseInfResponse result = warehouseService.createWarehouse(createWarehouseRequest);

		return ApiResponse.<WarehouseInfResponse>builder()
				.code(HttpStatus.CREATED.value())
				.result(result)
				.message("Create warehouse successfully")
				.build();
	}

	@PostMapping(
			value = "/update",
			consumes = "application/json",
			produces = "application/json")
	public ApiResponse<WarehouseInfResponse> updateWarehouse(
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody UpdateWarehouseRequest updateWarehouseRequest) throws Exception {
		try {
			WarehouseInfResponse result = warehouseService.updateWarehouse(updateWarehouseRequest, id);
			return ApiResponse.<WarehouseInfResponse>builder()
					.code(HttpStatus.CREATED.value())
					.result(result)
					.message("Update warehouse successfully")
					.build();
		} catch (Exception e) {
			log.error("Warehouse not found");
			throw new Exception("Warehouse not found");
		}
	}

	@GetMapping(
			value = "/{id}",
			produces = "application/json")
	public ApiResponse<WarehouseInfResponse> getWarehouseById(@PathVariable(name = "id") Long id) {
		WarehouseInfResponse result = warehouseService.getWareHouseById(id).orElseThrow(() -> new NotFoundException("Warehouse not found"));
		return ApiResponse.<WarehouseInfResponse>builder()
				.code(HttpStatus.OK.value())
				.result(result)
				.message("Get warehouse successfully")
				.build();
	}

	@DeleteMapping(
			value = "/{id}",
			produces = "application/json")
	public ApiResponse<WarehouseInfResponse> deleteWarehouse(@PathVariable(name = "id") Long id) {
		WarehouseInfResponse result = warehouseService.deleteWarehouse(id);
		return ApiResponse.<WarehouseInfResponse>builder()
				.code(HttpStatus.OK.value())
				.result(result)
				.message("Delete warehouse successfully")
				.build();
	}
}
