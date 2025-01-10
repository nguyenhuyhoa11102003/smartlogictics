package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findById(String id);
}
