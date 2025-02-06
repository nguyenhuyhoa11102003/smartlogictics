package com.tdtu.logistics_goods_service.service.implement;

import com.tdtu.logistics_goods_service.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
	ProductAttribute findByName(String name);
}