package com.tdtu.logistics_identity_service.service.implement;


import com.tdtu.logistics_identity_service.dto.request.CreateRoleRequest;
import com.tdtu.logistics_identity_service.dto.request.UpdateRoleRequest;
import com.tdtu.logistics_identity_service.dto.response.RoleResponseDTO;
import com.tdtu.logistics_identity_service.entity.Permission;
import com.tdtu.logistics_identity_service.entity.Role;
import com.tdtu.logistics_identity_service.enumrator.ErrorCode;
import com.tdtu.logistics_identity_service.exception.AppException;
import com.tdtu.logistics_identity_service.mapper.RoleMapper;
import com.tdtu.logistics_identity_service.repository.PermissionRepository;
import com.tdtu.logistics_identity_service.repository.RoleRepository;
import com.tdtu.logistics_identity_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    PermissionRepository permissionRepository;

    RoleMapper roleMapper;

    @Override
    public RoleResponseDTO createRole(CreateRoleRequest request) {
        log.info("Create role: {}", request);

        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTED);
        }
        Role role = roleMapper.toRole(request);

        Set<Permission> permissions = mapToPermissions(request.getPermissions());
        role.setPermissions(permissions);

        return roleMapper.toRoleResponseDTO(roleRepository.save(role));
    }

    @Override
    public RoleResponseDTO updateRole(UpdateRoleRequest request) {
        return null;
    }

    @Override
    public RoleResponseDTO getRoleById(String id) {
        return null;
    }

    @Override
    public RoleResponseDTO getRoleByName(String name) {
        return null;
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        return roles.stream().map(roleMapper::toRoleResponseDTO).toList();
    }

    @Override
    public void deleteRole(String id) {

    }

    private Set<Permission> mapToPermissions(List<String> permissions) {
        Set<Permission> permissionSet = new HashSet<>();
        permissions.stream().map(permissionRepository::findByName).forEach(permission -> {
            if (permission == null) {
                throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
            }

            permissionSet.add(permission);
        });

        return permissionSet;
    }
}

