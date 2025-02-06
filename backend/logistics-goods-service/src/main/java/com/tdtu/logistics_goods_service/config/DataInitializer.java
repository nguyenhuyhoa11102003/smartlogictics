package com.tdtu.logistics_goods_service.config;


import com.tdtu.logistics_goods_service.model.Category;
import com.tdtu.logistics_goods_service.model.ProductAttribute;
import com.tdtu.logistics_goods_service.repository.CategoryRepository;
import com.tdtu.logistics_goods_service.repository.ProductRepository;
import com.tdtu.logistics_goods_service.service.implement.ProductAttributeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner initData(
			CategoryRepository categoryRepository,
			ProductRepository productRepository,
			ProductAttributeRepository attributeRepo
	) {
		return args -> {
			if (categoryRepository.findAll().isEmpty() == Boolean.TRUE) {

				Category parcelCategory = Category.builder().name("Bưu kiện").description("Hàng hóa, gói hàng").build();
				Category documentCategory = Category.builder().name("Chứng từ").description("Giấy tờ, tài liệu").build();

				categoryRepository.saveAll(List.of(parcelCategory, documentCategory));
			}

			if (attributeRepo.findAll().isEmpty() == Boolean.TRUE){
				List<ProductAttribute> attributes = List.of(
						ProductAttribute.builder().name("Giá trị cao").build(),
						ProductAttribute.builder().name("Dễ vỡ").build(),
						ProductAttribute.builder().name("Nguyên khối").build(),
						ProductAttribute.builder().name("Quá cỡ").build(),
						ProductAttribute.builder().name("Chất lỏng").build(),
						ProductAttribute.builder().name("Từ tính").build(),
						ProductAttribute.builder().name("Hàng lạnh").build(),
						ProductAttribute.builder().name("Hóa đơn giấy chứng nhận").build(),
						ProductAttribute.builder().name("Hồ sơ thầu").build()
				);
				attributeRepo.saveAll(attributes);
			}
		};
	}
}
