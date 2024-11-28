package com.tdtu.logistics_goods_service.repository;


import com.tdtu.logistics_goods_service.model.Product;
import com.tdtu.logistics_goods_service.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	void deleteByImageIdInAndProduct(List<Long> imageIds, Product product);
}
