package com.tdtu.logistics_shipments_service.repository;

import com.tdtu.logistics_shipments_service.model.Shipment;
import com.tdtu.logistics_shipments_service.model.ShipmentSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentSegmentRepository extends JpaRepository<ShipmentSegment, Long> {

	List<ShipmentSegment> findByShipmentId(Long shipmentId);

	List<ShipmentSegment> findByShipmentAndSequenceOrderGreaterThan(Shipment shipment, Integer sequenceOrder);

	List<ShipmentSegment> findByShipmentAndSequenceOrderLessThan(Shipment shipment, Integer sequenceOrder);

	List<ShipmentSegment> findByShipment(Shipment shipment);

}