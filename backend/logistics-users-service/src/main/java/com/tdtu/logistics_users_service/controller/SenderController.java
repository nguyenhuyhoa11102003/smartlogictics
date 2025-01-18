package com.tdtu.logistics_users_service.controller;

import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.ApiResponse;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import com.tdtu.logistics_users_service.service.SenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/sender")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SenderController {

    SenderService senderService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ApiResponse<SenderInfResponse> createSender(
            @RequestBody CreateSenderRequest createSenderRequest) {
        SenderInfResponse result = senderService.createSender(createSenderRequest);

        return ApiResponse.<SenderInfResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .message("Create sender successfully")
                .build();
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ApiResponse<SenderInfResponse> updateSender(
            @PathVariable String id,
            @RequestBody UpdateSenderRequest updateSenderRequest) {
        SenderInfResponse result = senderService.updateSender(id, updateSenderRequest);

        return ApiResponse.<SenderInfResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Update sender successfully")
                .build();
    }
}
