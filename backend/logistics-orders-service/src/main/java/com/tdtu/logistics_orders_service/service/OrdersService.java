package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequestDTO;
import com.tdtu.logistics_orders_service.entity.Orders;

public interface OrdersService {

	Orders createOrder(CreateOrderRequestDTO createOrderRequestDTO);

}
