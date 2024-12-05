package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.entity.Payment;
import com.tdtu.logistics_orders_service.entity.Recipient;
import com.tdtu.logistics_orders_service.entity.Sender;
import com.tdtu.logistics_orders_service.mapper.PaymentMapper;
import com.tdtu.logistics_orders_service.mapper.RecipientMapper;
import com.tdtu.logistics_orders_service.mapper.SenderMapper;
import com.tdtu.logistics_orders_service.repository.OrdersRepository;
import com.tdtu.logistics_orders_service.repository.ShipperRepository;
import com.tdtu.logistics_orders_service.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersServiceImpl implements OrdersService {

	RecipientService recipientService;

	SenderService senderService;

	PaymentService paymentService;

	OrdersRepository ordersRepository;

	SenderMapper senderMapper;

	RecipientMapper recipientMapper;

	PaymentMapper paymentMapper;

	@Override
	public Orders createOrder(CreateOrderRequestDTO createOrderRequestDTO) {

		Sender sender = senderMapper.toSender(createOrderRequestDTO.getSender());
		Recipient recipient = recipientMapper.toRecipient(createOrderRequestDTO.getRecipient());
		Payment payment = paymentMapper.toPayment(createOrderRequestDTO.getPayment());

		Orders orders = Orders.builder()
				.shipmentCode(createOrderRequestDTO.getShipmentCode())
				.note(createOrderRequestDTO.getNote())
				.moreRequire(createOrderRequestDTO.getMoreRequire())
				.orderCode(createOrderRequestDTO.getOrderCode())
				.recipient(recipient)
				.sender(sender)
//				.status(createOrderRequestDTO.getStatusId())
//				.shipperId(createOrderRequestDTO.getShipperId())
				.payment(payment)
				.build();

		return ordersRepository.save(orders);
	}
}
