package com.tdtu.logistics_orders_service.viewmodel.order;


import com.tdtu.logistics_orders_service.entity.enumeration.EDeliveryMethod;
import com.tdtu.logistics_orders_service.entity.enumeration.EPaymentMethod;
import com.tdtu.logistics_orders_service.entity.enumeration.EPaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderPostVm(
		String email,
		OrderAddressPostVm shippingAddressPostVm,
		OrderAddressPostVm billingAddressPostVm,
		String note,
		float tax,
		float discount,
		int numberItem,
		BigDecimal totalPrice,
		BigDecimal deliveryFee,
		String couponCode,
		EDeliveryMethod deliveryMethod,
		EPaymentMethod paymentMethod,
		EPaymentStatus paymentStatus,
		String phoneNumber,
		Long idSeller,
		@NotNull
		List<OrderItemPostVm> orderItemPostVms) {
}
