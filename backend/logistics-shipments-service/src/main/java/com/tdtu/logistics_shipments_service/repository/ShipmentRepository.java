package com.tdtu.logistics_shipments_service.repository;


import com.tdtu.logistics_shipments_service.model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	Page<Shipment> findAll(Pageable pageable);
}
