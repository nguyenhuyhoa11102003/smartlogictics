package com.tdtu.logistics_goods_service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
public class Product extends AbstractMappedEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "sku", unique = true, nullable = false)
	private String sku;  // Mã hàng hóa

	@Column(name = "weight", precision = 10, scale = 2)
	private BigDecimal weight;  // Trọng lượng (kg)

	@Column(name = "dimensions")
	private String dimensions;  // Kích thước (dài x rộng x cao, ví dụ: 30x20x15 cm)

	@Column(name = "packaging_type")
	private String packagingType;  // Loại bao bì (hộp, kiện, pallet)

	@Column(name = "status")
	private String status;  // Trạng thái (đang vận chuyển, trong kho)

	@Column(name = "storage_location")
	private String storageLocation;  // Vị trí lưu trữ trong kho

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;  // Danh mục hàng hóa

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