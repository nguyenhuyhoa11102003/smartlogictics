package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    Boolean existsByName(String name);

    Permission findByName(String name);
}

