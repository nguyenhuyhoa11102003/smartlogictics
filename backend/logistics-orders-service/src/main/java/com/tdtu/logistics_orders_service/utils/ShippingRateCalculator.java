package com.tdtu.logistics_orders_service.utils;

import com.tdtu.logistics_orders_service.entity.ShippingRate;
import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ShippingZone;
import com.tdtu.logistics_orders_service.enumrator.TransportationType;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class ShippingRateCalculator {

	private static final Map<ShippingZone, Double> MAX_DISTANCE_MAP = new EnumMap<>(ShippingZone.class);
	private static final Map<TransportationType, BigDecimal> BASE_PRICE_MAP = new EnumMap<>(TransportationType.class);
	private static final Map<TransportationType, BigDecimal> PRICE_PER_KM_MAP = new EnumMap<>(TransportationType.class);

	static {
		// Khoảng cách tối đa theo khu vực
		MAX_DISTANCE_MAP.put(ShippingZone.NOI_TINH, 50.0);
		MAX_DISTANCE_MAP.put(ShippingZone.CAN_TINH, 200.0);
		MAX_DISTANCE_MAP.put(ShippingZone.LIEN_TINH, 500.0);

		// Giá cơ bản theo phương tiện (ĐÃ GIẢM)
		BASE_PRICE_MAP.put(TransportationType.XE_MAY, new BigDecimal("20000"));   // 20,000 VND
		BASE_PRICE_MAP.put(TransportationType.O_TO, new BigDecimal("35000"));     // 35,000 VND
		BASE_PRICE_MAP.put(TransportationType.XE_TAI, new BigDecimal("70000"));   // 70,000 VND
		BASE_PRICE_MAP.put(TransportationType.TAU_HOA, new BigDecimal("150000")); // 150,000 VND
		BASE_PRICE_MAP.put(TransportationType.MAY_BAY, new BigDecimal("700000")); // 700,000 VND

		// Giá mỗi km theo phương tiện (ĐÃ GIẢM)
		PRICE_PER_KM_MAP.put(TransportationType.XE_MAY, new BigDecimal("2000"));  // 2,000 VND/km
		PRICE_PER_KM_MAP.put(TransportationType.O_TO, new BigDecimal("4000"));    // 4,000 VND/km
		PRICE_PER_KM_MAP.put(TransportationType.XE_TAI, new BigDecimal("8000"));  // 8,000 VND/km
		PRICE_PER_KM_MAP.put(TransportationType.TAU_HOA, new BigDecimal("3500")); // 3,500 VND/km
		PRICE_PER_KM_MAP.put(TransportationType.MAY_BAY, new BigDecimal("15000")); // 15,000 VND/km
	}

	public static ShippingRate generateRate(DeliveryServiceType serviceType, ShippingZone shippingZone, TransportationType transportationType) {
		BigDecimal basePrice = BASE_PRICE_MAP.getOrDefault(transportationType, new BigDecimal("20000"));
		BigDecimal pricePerKm = PRICE_PER_KM_MAP.getOrDefault(transportationType, new BigDecimal("2000"));
		Double maxDistance = MAX_DISTANCE_MAP.getOrDefault(shippingZone, 50.0);

		return ShippingRate.builder()
				.serviceType(serviceType)
				.shippingZone(shippingZone)
				.transportationType(transportationType)
				.basePrice(basePrice)
				.pricePerKm(pricePerKm)
				.maxDistance(maxDistance)
				.build();
	}
}
