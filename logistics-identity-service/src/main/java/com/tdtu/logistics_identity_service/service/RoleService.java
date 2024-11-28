package com.tdtu.logistics_identity_service.service;

import com.tdtu.logistics_identity_service.dto.request.CreateRoleRequest;
import com.tdtu.logistics_identity_service.dto.request.UpdateRoleRequest;
import com.tdtu.logistics_identity_service.dto.response.RoleResponseDTO;

import java.util.List;

public interface RoleService {
    RoleResponseDTO createRole(CreateRoleRequest request);

    RoleResponseDTO updateRole(UpdateRoleRequest request);

    RoleResponseDTO getRoleById(String id);

    RoleResponseDTO getRoleByName(String name);

    List<RoleResponseDTO> getAllRoles();

    void deleteRole(String id);
}
