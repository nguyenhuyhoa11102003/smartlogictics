package com.tdtu.logistics_warehouse_service.repository;

import com.tdtu.logistics_warehouse_service.model.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.util.annotation.NonNullApi;


@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
	Page<Warehouse> findAll(Pageable pageable);

}
