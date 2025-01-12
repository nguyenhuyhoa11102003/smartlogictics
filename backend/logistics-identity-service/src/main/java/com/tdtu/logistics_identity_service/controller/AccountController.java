package com.tdtu.logistics_identity_service.controller;

import com.tdtu.logistics_identity_service.dto.request.ChangesPasswordRequest;
import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.logistics_identity_service.dto.response.AccountInfResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.ApiResponse;
import com.tdtu.logistics_identity_service.dto.response.CreateAccountResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.UserInfResponseDTO;
import com.tdtu.logistics_identity_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController("accountRestController")
@RequestMapping("/account")
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Account Management API", description = "APIs related to user account management")
public class AccountController {

    AccountService accountService;

    @Operation(summary = "Create a new account", description = "Create a new user account with registration details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Account successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateAccountResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> createAccount(
            @RequestBody CustomerRegisterAccountRequest createAccountRequest) {

        String result = accountService.createAccount(createAccountRequest);

        return ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .message("Create account successfully")
                .build();
    }

    @Operation(summary = "Update account password", description = "Update password for a specific account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountInfResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PutMapping(value = "/changes-password", consumes = "application/json", produces = "application/json")
    public ApiResponse<AccountInfResponseDTO> updatePassword(
            @RequestParam String accountId,
            @RequestBody @Valid ChangesPasswordRequest request) {

        log.info("Updating password for account ID: {}", accountId);

        AccountInfResponseDTO result = accountService.updatePassword(accountId, request);

        return ApiResponse.<AccountInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Password updated successfully")
                .build();
    }

    @Operation(summary = "Get account information", description = "Retrieve detailed information of the current logged-in account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account info retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfResponseDTO.class)))
    })
    @GetMapping(value = "/info", produces = "application/json")
    public ApiResponse<UserInfResponseDTO> info() {

        log.info("Get account inf_details...");

        return ApiResponse.<UserInfResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(accountService.getUserInfo())
                .message("Get account inf_details successfully")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get list of accounts", description = "Retrieve a paginated list of all accounts in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Accounts list retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountInfResponseDTO.class)))
    })
    @GetMapping(value = "/accounts", produces = "application/json")
    public ApiResponse<Page<AccountInfResponseDTO>> getAccounts(@PageableDefault(20) Pageable pageable) {
        log.debug("Get accounts...");
        Page<AccountInfResponseDTO> result = accountService.getAccounts(pageable);

        return ApiResponse.<Page<AccountInfResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Get list-accounts successfully")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all accounts", description = "Retrieve all accounts in the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All accounts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountInfResponseDTO.class)))
    })
    @GetMapping(value = "/all", produces = "application/json")
    public ApiResponse<List<AccountInfResponseDTO>> getAllAccounts() {

        log.info("Get all accounts...");

        return ApiResponse.<List<AccountInfResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(accountService.getAllAccounts())
                .message("Get all accounts successfully")
                .build();
    }
}
