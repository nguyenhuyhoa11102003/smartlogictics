package com.tdtu.logistics_goods_service.dto.req;


import com.tdtu.logistics_goods_service.model.Product;
import com.tdtu.logistics_goods_service.model.ProductAttribute;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public record ProductRequest(
		String name,
		double price,
		BigDecimal weight,
		String warehouseId,
		String packagingType,
		String storageLocation,
		Long categoryId,
		Set<String> attributes
) {
	// Factory method để tạo ProductRequest từ Product
	public static ProductRequest fromEntity(Product product) {

		Set<ProductAttribute> productAttributes = product.getAttributes();
		Set<String> name_attributes = new HashSet<>();
		for (ProductAttribute productAttribute : productAttributes) {
			name_attributes.add(productAttribute.getName());
		}

		return new ProductRequest(
				product.getName(),
				product.getPrice(),
				product.getWeight(),
				product.getWarehouseId(),
				product.getPackagingType(),
				product.getWarehouseId(),
				product.getCategory().getId(),
				name_attributes
		);
	}


}