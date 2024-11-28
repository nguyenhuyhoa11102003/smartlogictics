package com.tdtu.logistics_goods_service.service.implement;

import com.tdtu.logistics_goods_service.dto.req.CategoryRequest;
import com.tdtu.logistics_goods_service.dto.res.CategoryResponse;
import com.tdtu.logistics_goods_service.exception.wrapper.CategoryNotFoundException;
import com.tdtu.logistics_goods_service.model.Category;
import com.tdtu.logistics_goods_service.repository.CategoryRepository;
import com.tdtu.logistics_goods_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	public CategoryResponse createCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.name());
		category.setDescription(request.description());
		Category savedCategory = categoryRepository.save(category);
		return CategoryResponse.fromEntity(savedCategory);
	}

	@Override
	public Optional<CategoryResponse> getCategoryById(Long id) {
		return categoryRepository.findById(id).map(CategoryResponse::fromEntity);
	}

	@Override
	public Page<CategoryResponse> getAllCategoriesPaged(Pageable pageable) {
		return categoryRepository.findAll(pageable).map(CategoryResponse::fromEntity);
	}

	@Override
	public CategoryResponse updateCategory(Long id, CategoryRequest request) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + id));

		existingCategory.setName(request.name());
		existingCategory.setDescription(request.description());
		Category updatedCategory = categoryRepository.save(existingCategory);

		return CategoryResponse.fromEntity(updatedCategory);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.findById(id).ifPresentOrElse(
				categoryRepository::delete,
				() -> {
					throw new CategoryNotFoundException("Category not found with id " + id);
				}
		);
	}

	@Override
	public List<CategoryResponse> createMultipleCategories(List<CategoryRequest> categoryRequests) {
		List<Category> categories = categoryRequests.stream()
				.map(request -> {
					Category category = new Category();
					category.setName(request.name());
					category.setDescription(request.description());
					return category;
				})
				.collect(Collectors.toList());

		List<Category> savedCategories = categoryRepository.saveAll(categories);
		return savedCategories.stream().map(CategoryResponse::fromEntity).collect(Collectors.toList());
	}
}
