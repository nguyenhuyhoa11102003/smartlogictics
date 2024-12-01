package com.tdtu.logistics_identity_service.mapper;

import com.tdtu.logistics_identity_service.dto.request.CreateRoleRequest;
import com.tdtu.logistics_identity_service.dto.request.UpdateRoleRequest;
import com.tdtu.logistics_identity_service.dto.response.RoleResponseDTO;
import com.tdtu.logistics_identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(CreateRoleRequest request);

    RoleResponseDTO toRoleResponseDTO(Role role);

    @Mapping(target = "permissions", ignore = true)
    Role toRole(UpdateRoleRequest request);
}
