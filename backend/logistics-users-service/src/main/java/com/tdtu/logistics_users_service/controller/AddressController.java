package com.tdtu.logistics_users_service.controller;

import com.tdtu.logistics_users_service.dto.request.CreateAddressRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateAddressRequest;
import com.tdtu.logistics_users_service.dto.response.AddressInfResponse;
import com.tdtu.logistics_users_service.dto.response.ApiResponse;
import com.tdtu.logistics_users_service.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/address")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressController {

    AddressService addressService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ApiResponse<AddressInfResponse> createAddress(
            @RequestBody @Valid CreateAddressRequest createAddressRequest)
    {
        AddressInfResponse result = addressService.createAddress(createAddressRequest);

        return ApiResponse.<AddressInfResponse>builder()
                .code(200)
                .result(result)
                .message("Create address successfully")
                .build();
    }

    @PutMapping(value = "/{addressId}/update", consumes = "application/json", produces = "application/json")
    public ApiResponse<AddressInfResponse> updateAddress(
            @PathVariable Long addressId,
            @RequestBody @Valid UpdateAddressRequest updateAddressRequest)
    {
        AddressInfResponse result = addressService.updateAddress(addressId, updateAddressRequest);

        return ApiResponse.<AddressInfResponse>builder()
                .code(200)
                .result(result)
                .message("Update address successfully")
                .build();
    }

    @GetMapping(value = "/{addressId}", produces = "application/json")
    public ApiResponse<AddressInfResponse> getAddressById(@PathVariable Long addressId) {
        AddressInfResponse result = addressService.getAddressById(addressId);

        return ApiResponse.<AddressInfResponse>builder()
                .code(200)
                .result(result)
                .message("Get address by user id successfully")
                .build();
    }

}
