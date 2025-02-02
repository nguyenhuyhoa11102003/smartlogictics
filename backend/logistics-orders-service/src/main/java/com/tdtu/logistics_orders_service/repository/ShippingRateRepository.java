package com.tdtu.logistics_orders_service.repository;

import com.google.common.util.concurrent.RateLimiter;
import com.tdtu.logistics_orders_service.entity.ShippingRate;
import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ShippingZone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ShippingRateRepository extends CrudRepository<ShippingRate, Long> {
	Optional<ShippingRate> findShippingRateByServiceTypeAndShippingZone(DeliveryServiceType serviceType, ShippingZone shippingZone);
}
