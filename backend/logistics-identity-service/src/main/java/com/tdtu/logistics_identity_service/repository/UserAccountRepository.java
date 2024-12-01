package com.tdtu.logistics_identity_service.repository;

import com.tdtu.logistics_identity_service.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findById(String id);
}
