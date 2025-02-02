package com.tdtu.logistics_shipments_service.model;

import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "shipments")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment extends AbstractMappedEntity implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String trackingNumber;

	@Column(name = "shipper_id", nullable = false)
	Long shipper;

	@Column(name = "shipment_method", nullable = false)
	String shipmentMethod;

	@Column(name = "from_warehouse_id", nullable = false)
	Long fromWarehouseId;

	@Column(name = "intermediate_warehouse_ids")
	@ElementCollection
	List<Long> intermediateWarehouseIds = new ArrayList<>();

	@Column(name = "to_warehouse_id", nullable = false)
	Long toWarehouseId;

	@Enumerated(EnumType.STRING)
	@Column(name = "shipment_status", nullable = false)
	ShipmentStatus shipmentStatus =  ShipmentStatus.PENDING;

	@Column(name = "departure_time", nullable = false)
	LocalDateTime departureTime;

	@Column(name = "arrival_time")
	LocalDateTime arrivalTime;

	@Column(name = "orders", nullable = false)
	@ElementCollection
	List<String> orders = new ArrayList<>();

	@OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ShipmentSegment> shipmentSegments = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Shipment)) {
			return false;
		}
		return id != null && id.equals(((Shipment) o).id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
