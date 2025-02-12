package com.tdtu.logistics_payments_service.repository;

import com.tdtu.logistics_payments_service.model.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, String> {
}