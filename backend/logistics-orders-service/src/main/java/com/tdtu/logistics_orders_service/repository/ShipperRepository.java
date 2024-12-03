package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, String> {
}
