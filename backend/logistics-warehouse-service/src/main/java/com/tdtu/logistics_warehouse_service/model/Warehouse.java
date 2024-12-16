package com.tdtu.logistics_warehouse_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "warehouses")
@EntityListeners(AuditingEntityListener.class)
public class Warehouse extends AbstractMappedEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "warehouse_name", nullable = false)
	private String name;

	@Column(name = "warehouse_location", nullable = false)
	private String addressDetail;  // Địa chỉ kho

	private String phoneNumber;

	private double capacity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private WarehouseStatus status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
}
