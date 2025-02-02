package com.tdtu.logistics_orders_service.dto.model;

import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ShippingZone;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingRequestDTO {

	private DeliveryServiceType serviceType; // Loại dịch vụ (hỏa tốc, thường...)
	private ShippingZone shippingZone; // Khu vực (nội tỉnh, cận tỉnh, liên tỉnh)
	private double weight; // Trọng lượng
	private double length; // Chiều dài
	private double width; // Chiều rộng
	private double height; // Chiều cao
	private double distance; // Khoảng cách giao hàng
}
