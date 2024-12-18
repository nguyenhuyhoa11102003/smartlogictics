package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.dto.response.PaginatedResponse;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;

import java.util.List;

public interface OrdersService {

	OrderInfResponse createOrder(CreateOrderRequest requestDTO);

	boolean updateOrderStatus(String branchCode, String orderId, OrderStatus orderStatus);

	OrderInfResponse getOrderById(String orderId);

	List<OrderInfResponse> getOrderBySenderIdAndStatus(String senderId, OrderStatus status);

	PaginatedResponse<OrderInfResponse> getOrderBySenderId(String senderId , int page, int size);

}
