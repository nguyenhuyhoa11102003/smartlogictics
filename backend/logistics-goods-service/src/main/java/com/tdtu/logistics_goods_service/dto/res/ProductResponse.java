package com.tdtu.logistics_goods_service.dto.res;

import com.tdtu.logistics_goods_service.dto.req.ProductRequest;
import com.tdtu.logistics_goods_service.model.Product;

import java.math.BigDecimal;

public record ProductResponse(
		Long id,
		String name,
		String sku,
		BigDecimal weight,
		String dimensions,
		String packagingType,
		String status,
		String storageLocation,
		Long categoryId
) {

	public static ProductResponse fromEntity(Product product) {
		return new ProductResponse(
				product.getId(),
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

}