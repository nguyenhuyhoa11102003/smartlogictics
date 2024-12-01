package com.tdtu.logistics_goods_service.service;


import com.tdtu.logistics_goods_service.dto.req.ProductRequest;
import com.tdtu.logistics_goods_service.dto.res.ProductResponse;
import com.tdtu.logistics_goods_service.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;


public interface ProductService {
	ProductResponse createProduct(ProductRequest request);  // Tạo mới hàng hóa

	Optional<ProductResponse> getProductById(Long id);  // Lấy hàng hóa theo ID

	Page<ProductResponse> getAllProductsPaged(Pageable pageable); // Lấy tất cả hàng hóa

	ProductResponse updateProduct(Long id, ProductRequest request);  // Cập nhật hàng hóa

	void deleteProduct(Long id);  // Xóa hàng hóa
	List<ProductResponse> createMultipleProducts(List<ProductRequest> requests);
}

