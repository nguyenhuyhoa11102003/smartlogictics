package com.tdtu.logistics_inventory_service.repository;

import com.tdtu.logistics_inventory_service.model.LogisticsPackage;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<LogisticsPackage, Long> {
	List<LogisticsPackage> findLogisticsPackageByWarehouseId(Long warehouseId);
}
