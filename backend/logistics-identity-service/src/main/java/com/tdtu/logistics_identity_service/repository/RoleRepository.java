package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, String> {

    Boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
