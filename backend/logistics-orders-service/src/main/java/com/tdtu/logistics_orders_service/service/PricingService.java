package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.entity.Orders;

import java.math.BigDecimal;

public interface PricingService {
	BigDecimal calculateShippingCost(Orders order);
}