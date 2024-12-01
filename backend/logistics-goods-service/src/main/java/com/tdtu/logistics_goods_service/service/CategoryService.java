package com.tdtu.logistics_goods_service.service;

import com.tdtu.logistics_goods_service.dto.req.CategoryRequest;
import com.tdtu.logistics_goods_service.dto.res.CategoryResponse;
import com.tdtu.logistics_goods_service.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
	CategoryResponse createCategory(CategoryRequest request);

	Optional<CategoryResponse> getCategoryById(Long id);

	Page<CategoryResponse> getAllCategoriesPaged(Pageable pageable);

	CategoryResponse updateCategory(Long id, CategoryRequest request);

	void deleteCategory(Long id);

	List<CategoryResponse> createMultipleCategories(List<CategoryRequest> categoryRequests);

}
