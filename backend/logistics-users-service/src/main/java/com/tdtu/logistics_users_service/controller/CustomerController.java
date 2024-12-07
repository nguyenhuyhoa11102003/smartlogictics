package com.tdtu.logistics_users_service.controller;

import com.tdtu.logistics_users_service.dto.request.CreateCustomerRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateCustomerRequest;
import com.tdtu.logistics_users_service.dto.response.ApiResponse;
import com.tdtu.logistics_users_service.dto.response.CustomerInfResponse;
import com.tdtu.logistics_users_service.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerController {

    CustomerService customerService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ApiResponse<CustomerInfResponse> createCustomer(
            @RequestBody CreateCustomerRequest createCustomerRequest) {
        CustomerInfResponse result = customerService.createCustomer(createCustomerRequest);

        return ApiResponse.<CustomerInfResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .message("Create customer successfully")
                .build();
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ApiResponse<CustomerInfResponse> updateCustomer(
            @PathVariable String id,
            @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        CustomerInfResponse result = customerService.updateCustomer(id, updateCustomerRequest);

        return ApiResponse.<CustomerInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Update customer successfully")
                .build();
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ApiResponse<CustomerInfResponse> getCustomerById(@PathVariable String id) {
        CustomerInfResponse result = customerService.getCustomerById(id);

        return ApiResponse.<CustomerInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get customer by id successfully")
                .build();
    }

    @GetMapping(value = "/getByEmail/{email}", produces = "application/json")
    public ApiResponse<CustomerInfResponse> getCustomerByEmail(@PathVariable String email) {
        CustomerInfResponse result = customerService.getCustomerByEmail(email);

        return ApiResponse.<CustomerInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get customer by email successfully")
                .build();
    }

    @GetMapping(value = "/getByPhone/{phoneNumber}", produces = "application/json")
    public ApiResponse<CustomerInfResponse> getCustomerByPhone(@PathVariable String phoneNumber) {
        CustomerInfResponse result = customerService.getCustomerByPhoneNumber(phoneNumber);

        return ApiResponse.<CustomerInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get customer by phone successfully")
                .build();
    }
}
