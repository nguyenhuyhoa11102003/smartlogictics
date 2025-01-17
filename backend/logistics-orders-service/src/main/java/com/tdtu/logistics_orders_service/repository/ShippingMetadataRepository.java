package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.ShippingMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMetadataRepository extends CrudRepository<ShippingMetadata, Long> {

}
