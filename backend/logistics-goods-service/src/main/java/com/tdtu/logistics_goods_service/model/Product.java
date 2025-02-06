package com.tdtu.logistics_goods_service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
public class Product extends AbstractMappedEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price")
	private double price;  // Giá sản phẩm

	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;  // Trọng lượng (kg)

	@Column(name = "packaging_type")
	private String packagingType;  // Loại bao bì (hộp, kiện, pallet)

	@Column(name = "storage_location")
	private String warehouseId;  // Vị trí lưu trữ trong kho

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;  // Danh mục hàng hóa

	//	@ManyToMany
//	@JoinTable(
//			name = "product_attribute_mapping",
//			joinColumns = @JoinColumn(name = "product_id"),
//			inverseJoinColumns = @JoinColumn(name = "attribute_id")
//	)
//	private Set<ProductAttribute> attributes = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Product)) {
			return false;
		}
		Product product = (Product) o;
		return id != null && id.equals(product.id); // id is unique
	}

	@Override
	public int hashCode() {
		// see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}


}