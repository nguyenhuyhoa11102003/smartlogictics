<<<<<<< HEAD
package com.tdtu.logistics_inventory_service.dto.req;


import com.tdtu.logistics_goods_service.model.Product;

import java.math.BigDecimal;

public record ProductRequest(
		String name,
		String sku,
		BigDecimal weight,
		String dimensions,
		String packagingType,
		String status,
		String storageLocation,
		Long categoryId
) {
	// Factory method để tạo ProductRequest từ Product
	public static ProductRequest fromEntity(Product product) {
		return new ProductRequest(
				product.getName(),
				product.getSku(),
				product.getWeight(),
				product.getDimensions(),
				product.getPackagingType(),
				product.getStatus(),
				product.getStorageLocation(),
				product.getCategory() != null ? product.getCategory().getId() : null
		);
	}


=======
package com.tdtu.logistics_inventory_service.dto.req;


import com.tdtu.logistics_goods_service.model.Product;

import java.math.BigDecimal;

public record ProductRequest(
		String name,
		String sku,
		BigDecimal weight,
		String dimensions,
		String packagingType,
		String status,
		String storageLocation,
		Long categoryId
) {
	// Factory method để tạo ProductRequest từ Product
	public static ProductRequest fromEntity(Product product) {
		return new ProductRequest(
				product.getName(),
				product.getSku(),
				product.getWeight(),
				product.getDimensions(),
				product.getPackagingType(),
				product.getStatus(),
				product.getStorageLocation(),
				product.getCategory() != null ? product.getCategory().getId() : null
		);
	}


>>>>>>> develop
}