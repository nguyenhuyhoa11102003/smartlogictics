package com.tdtu.logistics_goods_service.model;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_attributes")
public class ProductAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "attribute_name", nullable = false, unique = true)
	private String name;  // Tên thuộc tính (e.g., "Dễ vỡ", "Giá trị cao")

}