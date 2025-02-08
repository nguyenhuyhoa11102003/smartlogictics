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

		// Giá cơ bản theo phương tiện
		BASE_PRICE_MAP.put(TransportationType.XE_MAY, new BigDecimal("15000"));
		BASE_PRICE_MAP.put(TransportationType.XE_TAI, new BigDecimal("20000"));
		BASE_PRICE_MAP.put(TransportationType.MAY_BAY, new BigDecimal("50000"));

		// Giá mỗi km theo phương tiện
		PRICE_PER_KM_MAP.put(TransportationType.XE_MAY, new BigDecimal("2000"));
		PRICE_PER_KM_MAP.put(TransportationType.XE_TAI, new BigDecimal("3000"));
		PRICE_PER_KM_MAP.put(TransportationType.MAY_BAY, new BigDecimal("10000"));
	}

	public static ShippingRate generateRate(DeliveryServiceType serviceType,
	                                        ShippingZone shippingZone,
	                                        TransportationType transportationType) {
		BigDecimal basePrice = BASE_PRICE_MAP.getOrDefault(transportationType, new BigDecimal("15000"));
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

	private static ShippingZone determineShippingZone(double distance) {
		if (distance <= 50.0) return ShippingZone.NOI_TINH;
		if (distance <= 200.0) return ShippingZone.CAN_TINH;
		return ShippingZone.LIEN_TINH;
	}

	private static TransportationType determineTransportationType(DeliveryServiceType serviceType, double distance) {
		if (serviceType == DeliveryServiceType.ECONOMY) {
			return (distance <= 50) ? TransportationType.XE_MAY : TransportationType.XE_TAI;
		}
//		if (serviceType == DeliveryServiceType.EXPRESS) {
//			return (distance <= 200) ? TransportationType.XE_TAI : TransportationType.;
//		}
//		if (serviceType == DeliveryServiceType.URGENT_SCHEDULED) {
//			return (distance <= 500) ? TransportationType.O_TO : TransportationType.MAY_BAY;
//		}
		return TransportationType.XE_MAY; // Mặc định nếu không xác định được
	}


}
