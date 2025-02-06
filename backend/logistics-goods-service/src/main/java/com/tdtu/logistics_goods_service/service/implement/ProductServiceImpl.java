package com.tdtu.logistics_goods_service.service.implement;


import com.tdtu.logistics_goods_service.dto.req.CategoryRequest;
import com.tdtu.logistics_goods_service.dto.req.ProductRequest;
import com.tdtu.logistics_goods_service.dto.res.ProductResponse;
import com.tdtu.logistics_goods_service.exception.wrapper.ProductNotFoundException;
import com.tdtu.logistics_goods_service.model.Category;
import com.tdtu.logistics_goods_service.model.Product;
import com.tdtu.logistics_goods_service.model.ProductAttribute;
import com.tdtu.logistics_goods_service.repository.CategoryRepository;
import com.tdtu.logistics_goods_service.repository.ProductRepository;
import com.tdtu.logistics_goods_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;


import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ProductAttributeRepository productAttributeRepository;

	@Override
	public ProductResponse createProduct(ProductRequest request) throws Exception {
		Product product = mapProductRequestToProduct(request);

//		Set<String> attributes = request.attributes();
//		for (String attribute : attributes) {
//			ProductAttribute productAttribute = productAttributeRepository.findByName(attribute);
//			if (productAttribute == null) {
//				throw new Exception("Không tìm thấy attribute với tên: " + attribute);
//			}
//			product.getAttributes().add(productAttribute);
//		}

		Product savedProduct = productRepository.save(product);
		return ProductResponse.fromEntity(savedProduct);
	}


	@Override
	public Optional<ProductResponse> getProductById(Long id) {
		return productRepository.findById(id).map(ProductResponse::fromEntity);
	}

	@Override
	public Page<ProductResponse> getAllProductsPaged(Pageable pageable) {
		return productRepository.findAll(pageable).map(ProductResponse::fromEntity);
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

		validateProductRequest(request);

		// If SKU is being updated, check if the new SKU is already taken
		//		if (request.sku() != null && !request.sku().equals(existingProduct.getSku())) {
		//			if (productRepository.existsBySku(request.sku())) {
		//				throw new DataIntegrityViolationException("Product with SKU " + request.sku() + " already exists");
		//			}
		//		}

		// If Category is being updated, check if the new Category exists
		if (request.categoryId() != null) {
			Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new IllegalArgumentException("Category not found with id " + request.categoryId()));
			existingProduct.setCategory(category);
		}

		existingProduct.setName(request.name());
		existingProduct.setWeight(request.weight());
		existingProduct.setPackagingType(request.packagingType());

		// Save the updated product to the database
		Product updatedProduct = productRepository.save(existingProduct);

		// Return the updated product as a ProductResponse
		return ProductResponse.fromEntity(updatedProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
		productRepository.delete(product);
	}

	@Override
	public List<ProductResponse> createMultipleProducts(List<ProductRequest> requests) {
		List<ProductResponse> productResponses = new ArrayList<>();
		for (ProductRequest request : requests) {
			// Validate the request
			validateProductRequest(request);
			// Map the request to a Product entity
			Product product = mapProductRequestToProduct(request);
			// Save the product
			Product savedProduct = productRepository.save(product);
			// Map the saved product to a response
			productResponses.add(ProductResponse.fromEntity(savedProduct));
		}
		return productResponses;
	}


	private Product mapProductRequestToProduct(ProductRequest request) {
		Product product = new Product();
		product.setName(request.name());
		product.setPrice(request.price());
		product.setWeight(request.weight());
		product.setWarehouseId(request.warehouseId());
		product.setPackagingType(request.packagingType());
		product.setCategory(getCategory(request.categoryId()));
		return product;
	}

	private Product getProduct(String id) {
		return this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
	}

	private Category getCategory(Long id) {
		return this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy type sản phẩm với ID: " + id));
	}


	private void validateProductRequest(ProductRequest request) {
		if (request.name() == null || request.name().isEmpty()) {
			throw new IllegalArgumentException("Product name is required");
		}

//		if (request.sku() == null || request.sku().isEmpty()) {
//			throw new IllegalArgumentException("Product SKU is required");
//		}

		if (request.weight() == null || request.weight().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Product weight must be greater than zero");
		}
	}
}
