package com.tdtu.logistics_users_service.controller;

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

@RestController()
@RequestMapping("/shipper")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShipperController {

    ShipperService shipperService;

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

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ApiResponse<ShipperInfResponse> getShipperById(@PathVariable String id) {
        ShipperInfResponse result = shipperService.getShipperInfById(id);

        return ApiResponse.<ShipperInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get shipper by id successfully")
                .build();
    }

    @GetMapping(value = "/staff/{staffId}", produces = "application/json")
    public ApiResponse<ShipperInfResponse> getShipperByStaffId(@PathVariable String staffId) {
        ShipperInfResponse result = shipperService.getShipperInfByStaffId(staffId);

        return ApiResponse.<ShipperInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get shipper by staff id successfully")
                .build();
    }

}
