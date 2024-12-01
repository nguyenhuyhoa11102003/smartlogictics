package com.tdtu.logistics_goods_service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true, exclude = {"subCategories", "parentCategory", "products"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category extends AbstractMappedEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;  // Tên danh mục (ví dụ: Điện tử, Thực phẩm)

	@Column(name = "description")
	private String description;  // Mô tả danh mục

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Category)) {
			return false;
		}
		Category category = (Category) o;
		return id != null && id.equals(category.id); // id is unique
	}

	@Override
	public int hashCode() {
		// see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}


}
