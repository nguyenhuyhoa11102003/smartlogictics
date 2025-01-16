package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.PaymentMetadata;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMetadataRepository extends PagingAndSortingRepository<PaymentMetadata, Long> {

}
