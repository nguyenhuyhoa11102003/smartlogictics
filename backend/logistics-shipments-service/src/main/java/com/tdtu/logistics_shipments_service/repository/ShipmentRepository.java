package com.tdtu.logistics_shipments_service.repository;


import com.tdtu.logistics_shipments_service.model.Shipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	@NotNull Page<Shipment> findAll(@NotNull Pageable pageable);

	//	Optional<Shipment> findFirstByShipmentStatusAndFromWarehouseIdAndShipmentMethodAndShipper(
	//			ShipmentStatus shipmentStatus,
	//			Long fromWarehouseId,
	//			String shipmentMethod,
	//			Long shipper);
}
