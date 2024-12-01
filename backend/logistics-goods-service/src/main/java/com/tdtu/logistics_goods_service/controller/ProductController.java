package com.tdtu.logistics_goods_service.controller;


import com.tdtu.logistics_goods_service.dto.req.ProductRequest;
import com.tdtu.logistics_goods_service.dto.res.ProductResponse;
import com.tdtu.logistics_goods_service.model.Product;
import com.tdtu.logistics_goods_service.service.ProductService;
import com.tdtu.logistics_goods_service.viewmodel.error.ErrorVm;
import com.tdtu.logistics_goods_service.viewmodel.product.ProductGetDetailVm;
import com.tdtu.logistics_goods_service.viewmodel.product.ProductPostVm;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(
			@RequestBody @Valid ProductRequest productRequest) {
		ProductResponse productResponse = productService.createProduct(productRequest);
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponse> productPage = productService.getAllProductsPaged(pageable);
		List<ProductResponse> productResponses = productPage.stream()
				.collect(Collectors.toList());
		return new ResponseEntity<>(productResponses, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable Long id,
			@RequestBody @Valid ProductRequest productRequest) {
		ProductResponse productResponse = productService.updateProduct(id, productRequest);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/batch")
	public ResponseEntity<List<ProductResponse>> createMultipleProducts(
			@RequestBody List<ProductRequest> productRequests) {
		List<ProductResponse> productResponses = productService.createMultipleProducts(productRequests);
		return new ResponseEntity<>(productResponses, HttpStatus.CREATED);
	}


}
