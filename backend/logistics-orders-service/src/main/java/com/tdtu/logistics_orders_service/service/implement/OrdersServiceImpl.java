package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import com.tdtu.logistics_orders_service.mapper.OrderMapper;
import com.tdtu.logistics_orders_service.mapper.PaymentMapper;
import com.tdtu.logistics_orders_service.repository.OrdersRepository;
import com.tdtu.logistics_orders_service.service.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersServiceImpl implements OrdersService {

    final OrdersRepository ordersRepository;

    final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderInfResponse createOrder(CreateOrderRequest requestDTO) {
        log.info("Logistic-Order-Service: Order-Service: Method-Create-order: {}", requestDTO);

        Orders orders = orderMapper.toEntity(requestDTO.getInformationOrder());
        orders.setStatus(requestDTO.getOrderCreationStatus());

        return orderMapper.toOrderInfResponse(ordersRepository.save(orders));
    }

    @Transactional
    @Override
    public OrderInfResponse updateOrderStatus(String orderId, OrderStatus orderStatus) {
        log.info("Logistic-Order-Service: Order-Service: Method-Update-order-status: {}", orderId);

        if (ordersRepository.existsById(orderId)) {

            ordersRepository.updateOrderStatusById(orderId, orderStatus);
            Orders orders = ordersRepository.findByOrderId(orderId);

            log.info("Logistic-Order-Service: Order-Service: Method-Update-order-status: {}", orders);
            return orderMapper.toOrderInfResponse(ordersRepository.save(orders));
        } else {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
    }

    @Override
    public OrderInfResponse getOrderById(String orderId) {
        log.info("Logistic-Order-Service: Order-Service: Method-Get-order-by-id: {}", orderId);

        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> {
            log.error("Logistic-Order-Service: Order-Service: Method-Get-order-by-id: Order not");

            return new AppException(ErrorCode.ORDER_NOT_FOUND);
        });

        return orderMapper.toOrderInfResponse(orders);
    }

    @Override
    public List<OrderInfResponse> getOrderBySenderIdAndStatus(String senderId, OrderStatus status) {
        log.info("Logistic-Order-Service: Order-Service: Method-Get-order-by-sender-id-and-status: {}", senderId);

        List<Orders> orders = ordersRepository.findBySenderIdAndStatus(senderId, status);

        return orders.stream().map(orderMapper::toOrderInfResponse).toList();
    }
}
