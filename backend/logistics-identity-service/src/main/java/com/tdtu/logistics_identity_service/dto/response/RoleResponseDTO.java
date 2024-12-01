package com.tdtu.logistics_identity_service.dto.response;

import com.tdtu.logistics_identity_service.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponseDTO {
    String id;

    String name;

    String description;

    Set<Permission> permissions;
}
