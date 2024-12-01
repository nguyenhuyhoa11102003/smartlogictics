package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Boolean existsByName(String name);

    Role findByName(String name);
}
