package com.tdtu.logistics_inventory_service.model;


import com.tdtu.logistics_inventory_service.enumrator.EnumPackage;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "packages")
public class LogisticsPackage  extends AbstractMappedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "warehouse_id", nullable = false)
	private Long warehouseId;

	@Column(name = "product_name", nullable = false)
	private String productName;  // lấy tên mã đơn hàng luôn

	@Column(name = "quantity", nullable = false)
	private int quantity; // Số lượng

	@Column(name = "length")
	private Double length; // Chiều dài (chỉ dành cho bưu kiện)

	@Column(name = "width")
	private Double width;  // Chiều rộng (chỉ dành cho bưu kiện)

	@Column(name = "height")
	private Double height; // Chiều cao (chỉ dành cho bưu kiện)

	@Column(name = "weight")
	private Double weight; // Trọng lượng

	@Column(name = "type_package", nullable = false)
	private EnumPackage typePackage = EnumPackage.PARCEL;

	@OneToMany(mappedBy = "logisticsPackage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PackageDetail> items = new ArrayList<>();

}
