package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, String> {

    List<Receiver> findByCustomer(String customerId);

}
