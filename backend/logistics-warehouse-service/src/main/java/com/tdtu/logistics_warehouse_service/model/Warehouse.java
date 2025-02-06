package com.tdtu.logistics_warehouse_service.model;

import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "warehouses")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Warehouse extends AbstractMappedEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "warehouse_name", nullable = false)
	String name;

	@Column(name = "warehouse_phone_number", nullable = false)
	String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	WarehouseStatus status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	Address address;

	double capacityUsed;

	double capacity;

	double volumeCapacity;

	double weightCapacity;

	double volumeUsed;

	double weightUsed;
}
