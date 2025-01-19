package com.tdtu.logistics_users_service.controller;

import com.tdtu.logistics_users_service.dto.model.ReceiverDetailDTO;
import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateReceiverRequest;
import com.tdtu.logistics_users_service.dto.response.ApiResponse;
import com.tdtu.common.user_service.ReceiverInfResponse;
import com.tdtu.logistics_users_service.service.ReceiverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<ReceiverDetailDTO> getReceiverById(@PathVariable String id) {
        ReceiverDetailDTO result = receiverService.getReceiverById(id);

        return ApiResponse.<ReceiverDetailDTO>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get receiver by id successfully")
                .build();
    }

//    @GetMapping(value = "/get-all/{customerId}", produces = "application/json")
//    public ApiResponse<List<ReceiverInfResponse>> getAllReceiversByCustomerId(@PathVariable String customerId) {
//        List<ReceiverInfResponse> result = receiverService.getAllReceiversByCustomerId(customerId);
//
//        return ApiResponse.<List<ReceiverInfResponse>>builder()
//                .code(HttpStatus.OK.value())
//                .result(result)
//                .message("Get all receivers by customer id successfully")
//                .build();
//    }

    @GetMapping(value = "/search-by-customer/{customerId}", produces = "application/json")
    public ApiResponse<Page<ReceiverDetailDTO>> searchByCustomerId(
            @PathVariable String customerId,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReceiverDetailDTO> result = receiverService.searchByCustomerIdDto(customerId, pageable);

        return ApiResponse.<Page<ReceiverDetailDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Search receivers by customerId successfully")
                .build();
    }

}
