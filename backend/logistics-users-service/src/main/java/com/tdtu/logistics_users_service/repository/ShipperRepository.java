package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Shipper;
import com.tdtu.logistics_users_service.enumrators.VehicleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;
import java.util.Set;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "shipper", path = "shipper")
public interface ShipperRepository extends PagingAndSortingRepository<Shipper, String> {

	Optional<Shipper> findByEmployeeCode(String employeeCode);

	Set<Shipper> findByWarehouseId(String warehouseId);

	Optional<Shipper> findById(String id);

	@Query("SELECT s FROM Shipper s WHERE s.available = true")
	List<Shipper> findAvailableShippers();

	@Query("SELECT s FROM Shipper s WHERE s.available = true AND s.vehicleType = ?1")
	List<Shipper> findByVehicleType(VehicleType vehicleType);

	@RestResource(exported = false)
	<S extends Shipper> S save(S entity);

	@RestResource(exported = false)
	boolean existsById(String id);

	@RestResource(exported = false)
	void delete(Shipper entity);


}
