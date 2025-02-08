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
	private static final double EARTH_RADIUS = 6371;


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




	public double calculateDistance(String pickupLatitude, String pickupLongitude, String deliveryLatitude, String deliveryLongitude) {
		double lat1Double = Double.parseDouble(pickupLatitude);
		double lon1Double = Double.parseDouble(pickupLongitude);
		double lat2Double = Double.parseDouble(deliveryLatitude);
		double lon2Double = Double.parseDouble(deliveryLongitude);

		double dLat = Math.toRadians(lat2Double - lat1Double);
		double dLon = Math.toRadians(lon2Double - lon1Double);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(lat1Double)) * Math.cos(Math.toRadians(lat2Double)) *
						Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS * c;
	}
}
