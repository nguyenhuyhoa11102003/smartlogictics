package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(itemResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findById(String id);

    @RestResource(exported = false)
    Account save(Account account);

    @RestResource(exported = false)
    void delete(Account account);
}
