package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.Account;
import com.tdtu.logistics_identity_service.repository.resources.excerpts.AccountExcerpt;
import feign.Param;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(itemResourceRel = "accounts", path = "accounts", excerptProjection = AccountExcerpt.class)
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findById(String id);

    @NotNull
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Account> findAll(@NotNull Pageable pageable);

    @RestResource(rel = "by-role-name", path = "by-role-name")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Query("SELECT a from account a JOIN a.roles r WHERE r.name = :roleName")
    Page<Account> findAccountsByRoleName(@Param("roleName") String roleName, Pageable pageable);


    @RestResource(exported = false)
    Account save(Account account);

    @RestResource(exported = false)
    void delete(Account account);
}
