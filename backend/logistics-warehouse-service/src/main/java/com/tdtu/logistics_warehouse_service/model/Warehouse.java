package com.tdtu.logistics_warehouse_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;
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

	Integer capacityUsed;
	double capacity;
}
