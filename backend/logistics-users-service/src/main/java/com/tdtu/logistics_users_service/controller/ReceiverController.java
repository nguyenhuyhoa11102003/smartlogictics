package com.tdtu.logistics_users_service.controller;

import com.tdtu.logistics_users_service.dto.request.CreateReceiverRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateReceiverRequest;
import com.tdtu.logistics_users_service.dto.response.ApiResponse;
import com.tdtu.logistics_users_service.dto.response.ReceiverInfResponse;
import com.tdtu.logistics_users_service.service.ReceiverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/receiver")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReceiverController {

    ReceiverService receiverService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ApiResponse<ReceiverInfResponse> createReceiver(
            @RequestBody CreateReceiverRequest createReceiverRequest) {
        ReceiverInfResponse result = receiverService.createReceiver(createReceiverRequest);

        return ApiResponse.<ReceiverInfResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .message("Create receiver successfully")
                .build();
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ApiResponse<ReceiverInfResponse> updateReceiver(
            @PathVariable String id,
            @RequestBody UpdateReceiverRequest updateReceiverRequest) {
        ReceiverInfResponse result = receiverService.updateReceiver(id, updateReceiverRequest);

        return ApiResponse.<ReceiverInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Update receiver successfully")
                .build();
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ApiResponse<ReceiverInfResponse> getReceiverById(@PathVariable String id) {
        ReceiverInfResponse result = receiverService.getReceiverById(id);

        return ApiResponse.<ReceiverInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get receiver by id successfully")
                .build();
    }

    @GetMapping(value = "/get-all/{customerId}", produces = "application/json")
    public ApiResponse<List<ReceiverInfResponse>> getAllReceiversByCustomerId(@PathVariable String customerId) {
        List<ReceiverInfResponse> result = receiverService.getAllReceiversByCustomerId(customerId);

        return ApiResponse.<List<ReceiverInfResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get all receivers by customer id successfully")
                .build();
    }

}
