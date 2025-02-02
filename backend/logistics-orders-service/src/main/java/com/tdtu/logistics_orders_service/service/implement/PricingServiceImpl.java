package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.service.PricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingServiceImpl implements PricingService {
	@Override
	public BigDecimal calculateShippingCost(Orders order) {
		return null;
	}
}
