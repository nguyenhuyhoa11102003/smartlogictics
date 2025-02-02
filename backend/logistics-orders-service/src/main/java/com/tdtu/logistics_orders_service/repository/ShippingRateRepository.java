package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.ShippingRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShippingRateRepository extends CrudRepository<ShippingRate, Long> {
}
