package com.tdtu.logistics_goods_service.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "product_image")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image_id")
	private Long imageId;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
