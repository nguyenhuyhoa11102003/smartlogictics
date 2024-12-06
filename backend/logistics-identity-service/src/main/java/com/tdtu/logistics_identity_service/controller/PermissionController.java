package com.tdtu.logistics_identity_service.controller;


import com.tdtu.logistics_identity_service.dto.request.CreatePermissionRequest;
import com.tdtu.logistics_identity_service.dto.response.ApiResponse;
import com.tdtu.logistics_identity_service.dto.response.PermissionResponseDTO;
import com.tdtu.logistics_identity_service.service.PermissionService;
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
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {

    PermissionService permissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ApiResponse<PermissionResponseDTO> createPermission(
            @RequestBody @Valid CreatePermissionRequest createPermissionRequest) {

        return ApiResponse.<PermissionResponseDTO>builder()
                .code(HttpStatus.OK.value())
                .result(permissionService.createPermission(createPermissionRequest))
                .message("Create permission successfully")
                .build();
    }

    //Get All Permissions
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = "application/json")
    public ApiResponse<List<PermissionResponseDTO>> getAllPermissions() {

        log.info("Get all permissions...");

        return ApiResponse.<List<PermissionResponseDTO>>builder()
                .code(HttpStatus.OK.value())
                .result(permissionService.getAllPermission())
                .message("Get all permissions successfully")
                .build();
    }

}
