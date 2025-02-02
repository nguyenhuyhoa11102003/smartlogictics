package com.tdtu.logistics_orders_service.configuration;

import com.tdtu.logistics_orders_service.entity.ShippingRate;
import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ShippingZone;
import com.tdtu.logistics_orders_service.repository.ShippingRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class ShippingRateSeeder implements CommandLineRunner {
	private final ShippingRateRepository shippingRateRepository;

	public ShippingRateSeeder(ShippingRateRepository shippingRateRepository) {
		this.shippingRateRepository = shippingRateRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// Kiểm tra xem bảng đã có dữ liệu chưa
		if (shippingRateRepository.count() == 0) {
			// Nếu bảng trống, thêm dữ liệu mới
			Arrays.stream(DeliveryServiceType.values()).forEach(serviceType ->
					Arrays.stream(ShippingZone.values()).forEach(shippingZone -> {
						ShippingRate shippingRate = ShippingRate.builder()
								.serviceType(serviceType)
								.shippingZone(shippingZone)
								.basePrice(generateBasePrice(serviceType, shippingZone))
								.pricePerKm(generatePricePerKm(shippingZone))
								.maxDistance(generateMaxDistance(shippingZone))
								.build();
						shippingRateRepository.save(shippingRate);
					})
			);
		}
	}


	// Hàm tạo giá cơ bản tùy theo loại dịch vụ và khu vực
	private BigDecimal generateBasePrice(DeliveryServiceType serviceType, ShippingZone shippingZone) {
		switch (serviceType) {
			case ECONOMY:
				return new BigDecimal("50000"); // Giá cơ bản cho dịch vụ hỏa tốc
			case EXPRESS:
				return new BigDecimal("20000"); // Giá cơ bản cho dịch vụ thường
			case URGENT_SCHEDULED:
				return new BigDecimal("100000"); // Giá cơ bản cho dịch vụ khác
			default:
				return new BigDecimal("10000");
		}
	}

	// Hàm tạo khoảng cách tối đa tùy theo khu vực
	private Double generateMaxDistance(ShippingZone shippingZone) {
		switch (shippingZone) {
			case NOI_TINH:
				return 100.0; // Khoảng cách tối đa cho nội tỉnh
			case CAN_TINH:
				return 200.0; // Khoảng cách tối đa cho cận tỉnh
			case LIEN_TINH:
				return 500.0; // Khoảng cách tối đa cho liên tỉnh
			default:
				return 100.0;
		}
	}

	// Hàm tạo giá mỗi km tùy theo khu vực
	private BigDecimal generatePricePerKm(ShippingZone shippingZone) {
		switch (shippingZone) {
			case NOI_TINH:
				return new BigDecimal("10000"); // Giá mỗi km cho nội tỉnh
			case CAN_TINH:
				return new BigDecimal("15000"); // Giá mỗi km cho cận tỉnh
			case LIEN_TINH:
				return new BigDecimal("20000"); // Giá mỗi km cho liên tỉnh
			default:
				return new BigDecimal("12000");
		}
	}

}