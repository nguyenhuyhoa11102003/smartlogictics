package com.tdtu.logistics_identity_service.controller;

import com.tdtu.logistics_identity_service.dto.request.AuthenticationRequest;
import com.tdtu.logistics_identity_service.dto.request.IntrospectTokenRequest;
import com.tdtu.logistics_identity_service.dto.request.LogoutRequest;
import com.tdtu.logistics_identity_service.dto.request.RefreshTokenRequest;
import com.tdtu.logistics_identity_service.dto.response.ApiResponse;
import com.tdtu.logistics_identity_service.dto.response.AuthenticationResponseDTO;
import com.tdtu.logistics_identity_service.dto.response.ErrorsResponse;
import com.tdtu.logistics_identity_service.dto.response.IntrospectTokenResponseDTO;
import com.tdtu.logistics_identity_service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;
    MessageSource messageSource;

    @Operation(summary = "Authenticate user with credentials", description = "Login and obtain authentication token.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully logged in",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthenticationResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid credentials or missing data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorsResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ApiResponse<AuthenticationResponseDTO> authenticated(@RequestBody @Valid AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(authenticationService.authenticated(request))
                .message(getLocalizedMessage("login_success"))
                .build();
    }

    @Operation(summary = "Logout user", description = "Invalidate the current session and token.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully logged out"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid logout request"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/logout", produces = "application/json")
    public ApiResponse<Void> logout(@RequestBody @Valid LogoutRequest request) {
        log.info("Logout...: {}", request.toString());
        authenticationService.logout(request);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(getLocalizedMessage("logout_success"))
                .build();
    }

    @Operation(summary = "Introspect token", description = "Verify the validity of the provided token.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Token introspected successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IntrospectTokenResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid token or missing data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/introspect", consumes = "application/json", produces = "application/json")
    public IntrospectTokenResponseDTO introspect(@RequestBody @Valid IntrospectTokenRequest request) {
        log.info("Introspect token...: {}", request.getToken());
        IntrospectTokenResponseDTO response = authenticationService.introspectToken(request);
        response.setMessage(getLocalizedMessage("token_valid"));

        log.info("Introspected token response have result is active: {}", response.toString());

        return response;
    }

    @Operation(summary = "Refresh authentication token", description = "Generate a new authentication token using the refresh token.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthenticationResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid refresh token or missing data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/refresh", consumes = "application/json", produces = "application/json")
    public ApiResponse<AuthenticationResponseDTO> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        log.info("Refresh token...: {}", request.toString());

        return ApiResponse.<AuthenticationResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(authenticationService.refreshToken(request))
                .message(getLocalizedMessage("refresh_token_generated"))
                .build();
    }

    private String getLocalizedMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

}
