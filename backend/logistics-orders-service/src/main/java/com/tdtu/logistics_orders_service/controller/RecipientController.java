package com.tdtu.logistics_orders_service.controller;

import com.tdtu.logistics_orders_service.dto.request.CreateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.dto.response.RecipientInfResponseDTO;
import com.tdtu.logistics_orders_service.service.RecipientService;
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
@RequestMapping("/recipient")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipientController {

    RecipientService recipientService;

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<RecipientInfResponseDTO> createRecipient(@RequestBody @Valid CreateRecipientRequestDTO recipientRequestDTO) {

        return ApiResponse.<RecipientInfResponseDTO>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create sender successfully")
                .result(recipientService.createRecipient(recipientRequestDTO))
                .build();
    }

    @PutMapping(value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<RecipientInfResponseDTO> updateRecipient(@PathVariable String id, @RequestBody @Valid UpdateRecipientRequestDTO updateRecipientRequestDTO) {

        return ApiResponse.<RecipientInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(recipientService.updateRecipient(id, updateRecipientRequestDTO))
                .message("Update sender successfully")
                .build();
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<RecipientInfResponseDTO> getRecipientById(@PathVariable String id) {

        return ApiResponse.<RecipientInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(recipientService.getRecipientById(id))
                .message("Get sender by id successfully")
                .build();
    }

    @GetMapping(value = "/email/{email}")
    public ApiResponse<RecipientInfResponseDTO> getRecipientByEmail(@PathVariable String email) {

        return ApiResponse.<RecipientInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(recipientService.getRecipientByEmail(email))
                .message("Get sender by email successfully")
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteRecipientById(@PathVariable String id) {
        recipientService.deleteRecipientById(id);
    }
}
