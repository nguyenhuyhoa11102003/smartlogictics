package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.model.ShippingRequestDTO;
import com.tdtu.logistics_orders_service.entity.ShippingRate;
import com.tdtu.logistics_orders_service.repository.ShippingRateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ShippingService {

	private final ShippingRateRepository shippingRateRepository;

	public ShippingService(ShippingRateRepository shippingRateRepository) {
		this.shippingRateRepository = shippingRateRepository;
	}

	public BigDecimal calculateShippingCost(ShippingRequestDTO requestDTO) {
		Optional<ShippingRate> shippingRate = shippingRateRepository.findShippingRateByServiceTypeAndShippingZone(requestDTO.getServiceType(), requestDTO.getShippingZone());

		if (shippingRate.isEmpty()) {
			throw new RuntimeException("Không tìm thấy thông tin phí vận chuyển cho loại dịch vụ và khu vực này.");
		}

		// Tính toán giá cơ bản + giá mỗi km
		ShippingRate existShippingRate = shippingRate.get();
		BigDecimal basePrice = existShippingRate.getBasePrice();
		BigDecimal pricePerKm = existShippingRate.getPricePerKm();
		double distance = requestDTO.getDistance();

		// Tính toán chi phí vận chuyển
		BigDecimal distanceCost = pricePerKm.multiply(BigDecimal.valueOf(distance));
		BigDecimal totalCost = basePrice.add(distanceCost);

//		// Nếu có trọng lượng lớn, có thể tính thêm phí cho hàng hóa quá khổ (ví dụ: vượt mức maxWeight)
//		BigDecimal weightFactor = BigDecimal.valueOf(requestDTO.getWeight()).multiply(new BigDecimal("0.1")); // Phí theo trọng lượng
//		totalCost = totalCost.add(weightFactor);

		return totalCost;
	}

}
