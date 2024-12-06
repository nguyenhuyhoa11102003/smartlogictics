package com.tdtu.logistics_identity_service.controller;


import com.tdtu.logistics_identity_service.dto.request.CreateRoleRequest;
import com.tdtu.logistics_identity_service.dto.response.ApiResponse;
import com.tdtu.logistics_identity_service.dto.response.RoleResponseDTO;
import com.tdtu.logistics_identity_service.service.implement.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleServiceImpl roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ApiResponse<RoleResponseDTO> createRole(@RequestBody @Valid CreateRoleRequest request) {

        RoleResponseDTO result = roleService.createRole(request);
        log.info("Created role: {}", result);

        return ApiResponse.<RoleResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Role created successfully")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = "application/json")
    public ApiResponse<List<RoleResponseDTO>> getAllRoles() {

        log.info("Get all roles...");

        return ApiResponse.<List<RoleResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(roleService.getAllRoles())
                .message("Get all roles successfully")
                .build();
    }
}

