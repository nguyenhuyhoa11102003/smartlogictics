package com.tdtu.logistics_inventory_service.model;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Inventory extends AbstractMappedEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "warehouse_id", nullable = false)
	private Long warehouseId;  // ID của kho hàng

	@Column(name = "product_id", nullable = false)
	private Long productId;  // ID của sản phẩm trong hệ thống (sẽ liên kết với Product Service)

	@Column(name = "quantity", nullable = false)
	private Long quantity;  // Số lượng hàng hóa hiện có trong kho

}
