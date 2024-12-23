package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.List;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, String> {

	Optional<Shipper> findByEmployeeCode(String employeeCode);

	Set<Shipper> findByWarehouseId(String warehouseId);

	@Query("SELECT s FROM Shipper s WHERE s.available = true")
	List<Shipper> findAvailableShippers();

}
