package com.tdtu.logistics_users_service.controller;

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


    @PutMapping(value = "/{userId}/update", consumes = "application/json", produces = "application/json")
    public ApiResponse<AddressInfResponse> updateAddress(
            @PathVariable String userId,
            @RequestBody @Valid UpdateAddressRequest updateAddressRequest)
    {
        AddressInfResponse result = addressService.updateAddress(userId, updateAddressRequest);

        return ApiResponse.<AddressInfResponse>builder()
                .code(200)
                .result(result)
                .message("Update address successfully")
                .build();
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public ApiResponse<AddressInfResponse> getAddressByUserId(@PathVariable String userId) {
        AddressInfResponse result = addressService.getAddressByUserId(userId);

        return ApiResponse.<AddressInfResponse>builder()
                .code(200)
                .result(result)
                .message("Get address by user id successfully")
                .build();
    }

}
