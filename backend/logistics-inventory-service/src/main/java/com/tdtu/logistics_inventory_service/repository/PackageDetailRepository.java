package com.tdtu.logistics_inventory_service.repository;

import com.tdtu.logistics_inventory_service.model.PackageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageDetailRepository extends JpaRepository<PackageDetail, Long> {
}
