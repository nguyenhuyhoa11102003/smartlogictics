package com.tdtu.logistics_goods_service.service;

import com.tdtu.logistics_goods_service.dto.req.ProductRequest;
import com.tdtu.logistics_goods_service.dto.res.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
	ProductResponse createProduct(ProductRequest request) throws Exception;  // Tạo mới hàng hóa

	Optional<ProductResponse> getProductById(Long id);  // Lấy hàng hóa theo ID

	Page<ProductResponse> getAllProductsPaged(Pageable pageable); // Lấy tất cả hàng hóa

	ProductResponse updateProduct(Long id, ProductRequest request);  // Cập nhật hàng hóa

	void deleteProduct(Long id);  // Xóa hàng hóa
	List<ProductResponse> createMultipleProducts(List<ProductRequest> requests);
}

