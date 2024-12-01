package com.tdtu.logistics_inventory_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "warehouses")
public class Warehouse extends AbstractMappedEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "warehouse_name", nullable = false)
	private String name;

	@Column(name = "warehouse_location", nullable = false)
	private String location;  // Địa chỉ kho

	@Column(name = "warehouse_type", nullable = false)
	private String type; // Ví dụ: kho lưu trữ, kho vận chuyển, kho đích

}
