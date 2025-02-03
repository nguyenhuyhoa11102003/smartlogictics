package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.request.DeliveryRequest;
import com.tdtu.logistics_orders_service.dto.request.PickupRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.dto.response.PaginatedResponse;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;

import java.util.List;

public interface OrdersService {


	OrderInfResponse createOrder(CreateOrderRequest requestDTO);

	void createMultipleOrders(List<CreateOrderRequest> requestList);

	boolean updateOrderStatus(String branchCode, String orderId, OrderStatus orderStatus);

	OrderInfResponse getOrderById(String orderId);

	List<OrderInfResponse> getOrderBySenderIdAndStatus(String senderId, OrderStatus status);

	PaginatedResponse<OrderInfResponse> getOrderBySenderId(String senderId, int page, int size);

	OrderInfResponse assignShipperPickUp(String orderId, String shipperId, PickupRequest pickupRequest);

	OrderInfResponse assignShipperDelivery(String orderId, String shipperId, DeliveryRequest pickupRequest);

	void updateShippingMetaData(String orderId, String shipmentId);


}
