package com.tdtu.logistics_orders_service.controller;

import com.tdtu.logistics_orders_service.dto.request.CreateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.dto.response.SenderInfResponseDTO;
import com.tdtu.logistics_orders_service.service.SenderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sender")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SenderController {

    SenderService senderService;

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SenderInfResponseDTO> createSender(@RequestBody @Valid CreateSenderRequestDTO createSenderRequestDTO) {

        return ApiResponse.<SenderInfResponseDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create sender successfully")
                .result(senderService.createSender(createSenderRequestDTO))
                .build();
    }

    @PutMapping(value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<SenderInfResponseDTO> updateSender(@PathVariable String id,
            @RequestBody @Valid UpdateSenderRequestDTO updateSenderRequestDTO) {

        return ApiResponse.<SenderInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(senderService.updateSender(id, updateSenderRequestDTO))
                .message("Update sender successfully")
                .build();
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<SenderInfResponseDTO> getSenderById(@PathVariable String id) {

        return ApiResponse.<SenderInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(senderService.getSenderById(id))
                .message("Get sender by id successfully")
                .build();
    }

    @GetMapping("/email/{email}")
    public ApiResponse<SenderInfResponseDTO> getSenderByEmail(@PathVariable String email) {

        return ApiResponse.<SenderInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(senderService.getSenderByEmail(email))
                .message("Get sender by email successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSenderById(@PathVariable String id) {

        senderService.deleteSenderById(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete sender successfully")
                .build();
    }
}
