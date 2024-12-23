package com.tdtu.logistics_users_service.controller;

import com.tdtu.logistics_users_service.dto.request.PickupRequest;
import com.tdtu.logistics_users_service.dto.request.CreateShipperRequest;
import com.tdtu.logistics_users_service.dto.response.ApiResponse;
import com.tdtu.logistics_users_service.dto.response.ShipperInfResponse;
import com.tdtu.logistics_users_service.service.ShipperService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController()
@RequestMapping("/shipper")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShipperController {

	ShipperService shipperService;

	// Create shipper
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ApiResponse<ShipperInfResponse> createShipper(
			@RequestBody @Valid CreateShipperRequest createShipperRequest) {
		ShipperInfResponse result = shipperService.createShipper(createShipperRequest);

		return ApiResponse.<ShipperInfResponse>builder()
				.code(HttpStatus.CREATED.value())
				.result(result)
				.message("Create shipper successfully")
				.build();
	}

	// Get shipper by id
	@GetMapping(value = "/get/{id}", produces = "application/json")
	public ApiResponse<ShipperInfResponse> getShipperById(@PathVariable String id) {
		ShipperInfResponse result = shipperService.getShipperInfById(id);

		return ApiResponse.<ShipperInfResponse>builder()
				.code(HttpStatus.OK.value())
				.result(result)
				.message("Get shipper by id successfully")
				.build();
	}

	// Get shipper by staff id
	@GetMapping(value = "/staff/{staffId}", produces = "application/json")
	public ApiResponse<ShipperInfResponse> getShipperByStaffId(@PathVariable String staffId) {
		ShipperInfResponse result = shipperService.getShipperInfByStaffId(staffId);

		return ApiResponse.<ShipperInfResponse>builder()
				.code(HttpStatus.OK.value())
				.result(result)
				.message("Get shipper by staff id successfully")
				.build();
	}

	// Get shipper by warehouse id
	@GetMapping(value = "/warehouse/{warehouseId}", produces = "application/json")
	public ApiResponse<Set<ShipperInfResponse>> getShipperByWarehouse(@PathVariable String warehouseId) {
		Set<ShipperInfResponse> result = shipperService.getShipperByWarehouse(warehouseId);

		return ApiResponse.<Set<ShipperInfResponse>>builder()
				.code(HttpStatus.OK.value())
				.result(result)
				.message("Get shipper by warehouse id successfully")
				.build();
	}


	// POST: create a pickup request for a package
	@PostMapping(value = "/{shipperId}/pickup", consumes = "application/json", produces = "application/json")
	public ApiResponse<ShipperInfResponse> collectGoodsFromCustomer(
			@RequestBody PickupRequest pickupRequest
	) {
		return ApiResponse.<ShipperInfResponse>builder()
				.code(HttpStatus.OK.value())
				.result(null)
				.message("Assign order to shipper successfully")
				.build();
	}
}
