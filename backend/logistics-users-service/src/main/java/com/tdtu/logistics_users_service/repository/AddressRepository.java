package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, String> {

    Optional<Address> findByUserId(String userId);
}
