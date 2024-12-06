package com.tdtu.logistics_identity_service.controller;


import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.logistics_identity_service.dto.request.CreateAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.ApiResponse;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;
import com.tdtu.logistics_identity_service.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/account")
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountController {
    UserAccountService userAccountService;

    //Create Account
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<CreateAccountResponseDTO> createAccount(
            @RequestBody CreateAccountRequest createAccountRequest) {
        CreateAccountResponseDTO result = userAccountService.createAccount(createAccountRequest);

        return ApiResponse.<CreateAccountResponseDTO>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .message("Create account successfully")
                .build();
    }

    // Update Password
    @PutMapping(value = "/changes-password", consumes = "application/json", produces = "application/json")
    public ApiResponse<AccountInfResponseDTO> updatePassword(
            @RequestParam String accountId,
            @RequestBody @Valid ChangesPasswordRequest request) {

        log.info("Updating password for account ID: {}", accountId);

        AccountInfResponseDTO result = userAccountService.updatePassword(accountId, request);

        return ApiResponse.<AccountInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Password updated successfully")
                .build();
    }

    //Get Account By ID
    @GetMapping(value = "/info", produces = "application/json")
    public ApiResponse<UserInfResponseDTO> info() {

        log.info("Get account inf_details...");

        return ApiResponse.<UserInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(userAccountService.getUserInfo())
                .message("Get account inf_details successfully")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/accounts", produces = "application/json")
    public ApiResponse<Page<AccountInfResponseDTO>> getAccounts(@PageableDefault(20) Pageable pageable) {
        log.debug("Get accounts...");
        Page<AccountInfResponseDTO> result = userAccountService.getAccounts(pageable);

        return ApiResponse.<Page<AccountInfResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get list-accounts successfully")
                .build();
    }

    //Get All Accounts
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = "application/json")
    public ApiResponse<List<AccountInfResponseDTO>> getAllAccounts() {

        log.info("Get all accounts...");

        return ApiResponse.<List<AccountInfResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(userAccountService.getAllAccounts())
                .message("Get all accounts successfully")
                .build();
    }
}
