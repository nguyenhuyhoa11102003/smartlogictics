package com.tdtu.logistics_inventory_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "package_detail")
public class PackageDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", nullable = false)
	LogisticsPackage logisticsPackage;

	@Column(name = "product_name", nullable = false)
	String productName;

	@Column(name = "quantity", nullable = false)
	int quantity;

	@Column(name = "weight")
	Double weight;

	@Column(name = "length")
	Double length;

	@Column(name = "width")
	Double width;

	@Column(name = "height")
	Double height;

	@Column(name = "price")
	Double price;

	@Column(name = "description")
	String description;

	@Column(name = "image")
	String image;

	@Column(name = "type")
	EnumType type;


}
