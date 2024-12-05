package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SenderRepository extends JpaRepository<Sender, String> {

    Optional<Sender> findByEmail(String email);
}
