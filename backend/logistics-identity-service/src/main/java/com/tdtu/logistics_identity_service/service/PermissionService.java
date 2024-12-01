package com.tdtu.logistics_identity_service.service;

import com.tdtu.logistics_identity_service.dto.request.CreatePermissionRequest;
import com.tdtu.logistics_identity_service.dto.response.PermissionResponseDTO;

import java.util.List;

public interface PermissionService {
    List<PermissionResponseDTO> getAllPermission();

    List<PermissionResponseDTO> getAllPermissionByRoleName(String roleName);

    PermissionResponseDTO createPermission(CreatePermissionRequest permission);

    void deletePermission(String permissionName);
}
