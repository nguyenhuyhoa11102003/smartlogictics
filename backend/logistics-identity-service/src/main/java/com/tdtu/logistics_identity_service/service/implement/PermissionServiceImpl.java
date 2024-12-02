package com.tdtu.logistics_identity_service.service.implement;


import com.tdtu.logistics_identity_service.dto.request.CreatePermissionRequest;
import com.tdtu.logistics_identity_service.dto.response.PermissionResponseDTO;
import com.tdtu.logistics_identity_service.entity.Permission;
import com.tdtu.logistics_identity_service.exception.ErrorCode;
import com.tdtu.logistics_identity_service.exception.AppException;
import com.tdtu.logistics_identity_service.mapper.PermissionMapper;
import com.tdtu.logistics_identity_service.repository.PermissionRepository;
import com.tdtu.logistics_identity_service.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    @Override
    public List<PermissionResponseDTO> getAllPermission() {

        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream().map(permissionMapper::toPermissionResponseDTO).toList();
    }

    @Override
    public List<PermissionResponseDTO> getAllPermissionByRoleName(String roleName) {
        return null;
    }

    @Override
    public PermissionResponseDTO createPermission(CreatePermissionRequest permission) {

        return permissionMapper.toPermissionResponseDTO(
                permissionRepository.save(permissionMapper.toPermission(permission))
        );
    }

    @Override
    public void deletePermission(String permissionName) {
        Permission permission = permissionRepository.findByName(permissionName);
        if (ObjectUtils.isEmpty(permission)) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        } else {
            permissionRepository.delete(permission);
        }
    }
}

