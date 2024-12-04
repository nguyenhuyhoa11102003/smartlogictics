package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.viewmodel.order.OrderPostVm;
import com.tdtu.logistics_orders_service.viewmodel.order.OrderVm;

public interface OrderService {

	OrderVm createOrder(OrderPostVm orderPostVm);

}
