package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, String> {

    Optional<Recipient> findByEmail(String email);
}
