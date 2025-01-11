package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(exported = false)
public interface InvalidTokenRepository extends JpaRepository<InvalidatedToken, String> {

}

