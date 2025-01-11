package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PermissionRepository extends JpaRepository<Permission, String> {

    Boolean existsByName(String name);

    Permission findByName(String name);
}

