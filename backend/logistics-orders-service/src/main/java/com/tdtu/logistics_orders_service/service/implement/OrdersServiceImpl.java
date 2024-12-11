package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.common.constant.KafkaTopic;
import com.tdtu.common.dto.MailUpdateOrderStatus;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.common.user_service.dto.ReceiverInfResponse;
import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import com.tdtu.logistics_orders_service.mapper.OrderMapper;
import com.tdtu.logistics_orders_service.repository.OrdersRepository;
import com.tdtu.logistics_orders_service.service.*;
import com.tdtu.logistics_orders_service.service.client.UserServiceClient;
import com.tdtu.logistics_orders_service.utils.OrderStatusValidator;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersServiceImpl implements OrdersService {

    final OrdersRepository ordersRepository;

    final OrderMapper orderMapper;

    final UserServiceClient userServiceClient;

    final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @Override
    public OrderInfResponse createOrder(CreateOrderRequest requestDTO) {
        log.info("Logistic-Order-Service: Order-Service: Method-Create-order: {}", requestDTO);

        Orders orders = orderMapper.toEntity(requestDTO.getInformationOrder());
        orders.setStatus(requestDTO.getOrderCreationStatus());

        CustomerInfResponse customerInfResponse = userServiceClient.getCustomerById(orders.getSenderId()).getResult();
        MailUpdateOrderStatus mailUpdateOrderStatus = MailUpdateOrderStatus.builder()
                .to(customerInfResponse.getEmail())
                .subject("Dear"+ customerInfResponse.getFullName() +"Your order have bean status update: "+orders.getOrderCode())
                .build();

        kafkaTemplate.send(KafkaTopic.UPDATE_ORDER, mailUpdateOrderStatus);

        return orderMapper.toOrderInfResponse(ordersRepository.save(orders));
    }

    @Transactional
    @Override
    public boolean updateOrderStatus(String branchCode, String orderId, OrderStatus orderStatus) {
        log.info("Logistic-Order-Service: Order-Service: Method-Update-order-status: {}", orderId);
        Orders orders = ordersRepository.findByOrderIdAndBranchCode(orderId, branchCode).orElseThrow(
                () -> {
                    log.error("Logistic-Order-Service: Order-Service: Method-Update-order-status: Order not found");
                    return new AppException(ErrorCode.ORDER_NOT_FOUND);
                }
        );

        log.error("Logistic-Order-Service: Order-Service: Method-Update-order-status: Order status: {}", orders.getStatus());

        if (!OrderStatusValidator.inValid(orders.getStatus())) {

            ordersRepository.updateOrderStatusById(branchCode, orderId, orderStatus);

            // Send message to notification service
            CustomerInfResponse customerInfResponse = userServiceClient.getCustomerById(orders.getSenderId()).getResult();
            MailUpdateOrderStatus mailUpdateOrderStatus = MailUpdateOrderStatus.builder()
                    .to(customerInfResponse.getEmail())
                    .subject("Dear "+ customerInfResponse.getFullName() +" Your order id:" + orders.getId() + " have bean status update: "+orderStatus)
                    .build();

            kafkaTemplate.send(KafkaTopic.UPDATE_ORDER, mailUpdateOrderStatus);

            ReceiverInfResponse receiverInfResponse = userServiceClient.getReceiverById(orders.getRecipientId()).getResult();
            MailUpdateOrderStatus mailUpdateOrderStatusReceiver = MailUpdateOrderStatus.builder()
                    .to(receiverInfResponse.getEmail())
                    .subject("Dear"+ receiverInfResponse.getFullName() +"Your order have bean status update: "+orders.getOrderCode())
                    .build();
            kafkaTemplate.send(KafkaTopic.UPDATE_ORDER, mailUpdateOrderStatusReceiver);

            log.info("Logistic-Order-Service: Order-Service: Method-Update-order-status: Order status updated have been sent to notification service");

            return true;
        } else {
            throw new AppException(ErrorCode.ORDER_STATUS_NOT_VALID);
        }
    }

    @Override
    public OrderInfResponse getOrderById(String orderId) {
        log.info("Logistic-Order-Service: Order-Service: Method-Get-order-by-id: {}", orderId);

        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> {
            log.error("Logistic-Order-Service: Order-Service: Method-Get-order-by-id: Order not");

            return new AppException(ErrorCode.ORDER_NOT_FOUND);
        });

        return setSenderField(orders, orderMapper.toOrderInfResponse(orders));
    }

    @Override
    public List<OrderInfResponse> getOrderBySenderIdAndStatus(String senderId, OrderStatus status) {
        log.info("Logistic-Order-Service: Order-Service: Method-Get-order-by-sender-id-and-status: {}", senderId);

        List<Orders> orders = ordersRepository.findBySenderIdAndStatus(senderId, status);

        return orders.stream().map(order -> {
            OrderInfResponse orderInfResponse = orderMapper.toOrderInfResponse(order);
            return setSenderField(order, orderInfResponse);
        }).toList();
    }

    private OrderInfResponse setSenderField(Orders orders, OrderInfResponse orderInfResponse) {
        log.info("Logistic-Order-Service: Order-Service: Method-Set-sender-field: {}", orderInfResponse);

        CustomerInfResponse customerInfResponse = userServiceClient.getCustomerById(orders.getSenderId()).getResult();
        ReceiverInfResponse receiverInfResponse = userServiceClient.getReceiverById(orders.getRecipientId()).getResult();

        //AddressInfResponse addressInfResponse = userServiceClient.getAddressByUserId(orders.getSenderId()).getResult();

        //Fix shipmentCode = ShipperId::
        //ShipperInfResponse shipperInfResponse = userServiceClient.getShipperById(orders.getShipmentCode()).getResult();

        orderInfResponse.setSenderName(customerInfResponse.getFullName());
        orderInfResponse.setSenderPhone(customerInfResponse.getPhoneNumber());
        orderInfResponse.setSenderEmail(customerInfResponse.getEmail());

        orderInfResponse.setReceiverName(receiverInfResponse.getFullName());
        orderInfResponse.setReceiverPhone(receiverInfResponse.getPhoneNumber());
        orderInfResponse.setReceiverEmail(receiverInfResponse.getEmail());

        orderInfResponse.setSenderCode(customerInfResponse.getCustomerCode());

        return orderInfResponse;
    }
}
