package com.tdtu.logistics_orders_service.entity;

import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ShippingZone;
import com.tdtu.logistics_orders_service.enumrator.TransportationType;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shipping_rate")
public class ShippingRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "service_type", nullable = false)
	DeliveryServiceType serviceType; // HỎA TỐC, THƯỜNG

	@Enumerated(EnumType.STRING)
	@Column(name = "shipping_zone", nullable = false)
	private ShippingZone shippingZone; // NỘI_TỈNH, CẬN_TỈNH, LIÊN_TỈNH

	@Column(name = "base_price", precision = 19, scale = 2, nullable = false)
	private BigDecimal basePrice; // Giá cơ bản

	@Enumerated(EnumType.STRING)
	@Column(name = "transportation_type", nullable = false)
	private TransportationType transportationType; // XE_MÁY, O_TÔ, XE_TẢI, TÀU_HỎA, MÁY_BAY

	@Column(name = "price_per_km", precision = 19, scale = 2, nullable = false)
	private BigDecimal pricePerKm; // Giá mỗi km

	@Column(name = "max_distance")
	private Double maxDistance; // Khoảng cách tối đa áp dụng mức giá này (km)
}
