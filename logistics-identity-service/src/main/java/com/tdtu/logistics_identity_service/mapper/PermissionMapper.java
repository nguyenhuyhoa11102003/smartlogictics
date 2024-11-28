package com.tdtu.logistics_identity_service.mapper;

import com.tdtu.logistics_identity_service.dto.request.CreatePermissionRequest;
import com.tdtu.logistics_identity_service.dto.response.PermissionResponseDTO;
import com.tdtu.logistics_identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(CreatePermissionRequest createPermissionRequest);

    PermissionResponseDTO toPermissionResponseDTO(Permission permission);

}
