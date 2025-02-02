package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.common.constant.KafkaTopic;
import com.tdtu.common.dto.MailUpdateOrderStatus;
import com.tdtu.common.orchestration.workflow.CreateReceiverWorkflow;
import com.tdtu.common.orchestration.workflow.WorkerHelper;
import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.common.user_service.dto.ReceiverInfResponse;
import com.tdtu.common.user_service.dto.ShipperInfResponse;
import com.tdtu.logistics_orders_service.dto.model.ShippingRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.request.DeliveryRequest;
import com.tdtu.logistics_orders_service.dto.request.PickupRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.dto.response.PaginatedResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.entity.PaymentMetadata;
import com.tdtu.logistics_orders_service.entity.ShippingMetadata;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import com.tdtu.logistics_orders_service.enumrator.PaymentStatus;
import com.tdtu.logistics_orders_service.enumrator.PaymentType;
import com.tdtu.logistics_orders_service.enumrator.ReceivingMethod;
import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import com.tdtu.logistics_orders_service.mapper.OrderMapper;
import com.tdtu.logistics_orders_service.repository.OrdersRepository;
import com.tdtu.logistics_orders_service.repository.PaymentMetadataRepository;
import com.tdtu.logistics_orders_service.repository.ShippingMetadataRepository;
import com.tdtu.logistics_orders_service.service.*;
import com.tdtu.logistics_orders_service.service.client.UserServiceClient;
import com.tdtu.logistics_orders_service.utils.OrderStatusValidator;
import com.tdtu.logistics_orders_service.utils.SecurityContextCustomer;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersServiceImpl implements OrdersService {

	final OrdersRepository ordersRepository;

	final OrderMapper orderMapper;

	final UserServiceClient userServiceClient;

	final ShippingMetadataRepository shippingMetadataRepository;

	final PaymentMetadataRepository paymentMetadataRepository;

	final KafkaTemplate<String, Object> kafkaTemplate;

	final WorkflowClient workflowClient;

	final ShippingService shippingService;

	@Transactional
	@Override
	public OrderInfResponse createOrder(CreateOrderRequest requestDTO) {
		log.info("Logistic-Order-Service: Order-Service: Method-Create-order: {}", requestDTO);

		String customerId = SecurityContextCustomer.getCustomerId();
		if (customerId == null) {
			log.error("Logistic-Order-Service: Order-Service: Method-Create-order: Customer not found");
			throw new RuntimeException("Customer not found");
		}

		ShippingMetadata shippingMetadata = toShippingMetadata(requestDTO);
		shippingMetadataRepository.save(shippingMetadata);
		log.info("Logistic-Order-Service: Order-Service: Method-Create-order: Shipping metadata saved");

		PaymentMetadata paymentMetadata = toPaymentMetadata(requestDTO);
		paymentMetadataRepository.save(paymentMetadata);
		log.info("Logistic-Order-Service: Order-Service: Method-Create-order: Payment metadata saved");

		String receiverId = createReceiver(customerId, requestDTO);
		Orders orderEntity = toOrder(customerId, requestDTO, shippingMetadata, paymentMetadata, receiverId);

		if (Objects.nonNull(receiverId)) {

			// 		FIXME: 2025-01-20 : Notification service
			//		CustomerInfResponse customerInfResponse = userServiceClient.getCustomerById(orders.getSenderId()).getResult();
			//
			//		MailUpdateOrderStatus mailUpdateOrderStatus = MailUpdateOrderStatus.builder()
			//				.to(customerInfResponse.getEmail())
			//				.subject("Dear" + customerInfResponse.getFullName() + "Your order have bean status update: " + orders.getOrderCode())
			//				.build();
			//		kafkaTemplate.send(KafkaTopic.UPDATE_ORDER, mailUpdateOrderStatus);
			// 		FIXME: 2025-01-20 : Notification service

			log.info("Logistic-Order-Service: Order-Service: Method-Create-order: Receiver created");
			return orderMapper.toOrderInfResponse(ordersRepository.save(orderEntity));
		} else {

			throw new AppException(ErrorCode.CREATE_ORDER_FAILED);
		}
	}

	@Override
	@Transactional
	public void createMultipleOrders(List<CreateOrderRequest> requestList) {
		// Lấy thông tin người dùng từ SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String customerId = null;

		if (authentication != null && authentication.isAuthenticated()) {
			Jwt jwt = (Jwt) authentication.getPrincipal();
			customerId = (String) jwt.getClaims().get("customerId");
		}

		List<Orders> ordersToSave = new ArrayList<>();

		for (CreateOrderRequest requestDTO : requestList) {
			ShippingMetadata shippingMetadata = toShippingMetadata(requestDTO);
			shippingMetadataRepository.save(shippingMetadata);

			PaymentMetadata paymentMetadata = toPaymentMetadata(requestDTO);
			paymentMetadataRepository.save(paymentMetadata);

			String receiverId = createReceiver(customerId, requestDTO);
			Orders orderEntity = toOrder(customerId, requestDTO, shippingMetadata, paymentMetadata, receiverId);

			if (receiverId != null) {
				ordersToSave.add(orderEntity);
			} else {
				throw new AppException(ErrorCode.CREATE_ORDER_FAILED);
			}
		}

		// Save all orders in a batch
		ordersRepository.saveAll(ordersToSave);
	}

	private String createReceiver(String customerId, CreateOrderRequest requestDTO) {
		try {
			WorkflowOptions options = WorkflowOptions.newBuilder()
					.setTaskQueue(WorkerHelper.WORKFLOW_CREATE_ORDER_TASK_QUEUE)
					.build();

			log.info("Logistic-Order-Service: Order-Service: Method-Create-receiver: {}", requestDTO);

			CreateReceiverRequest request = CreateReceiverRequest.builder()
					.fullName(requestDTO.getInformationOrder().getRecipientName())
					.phoneNumber(requestDTO.getInformationOrder().getReceiverPhone())
					.email(requestDTO.getInformationOrder().getReceiverEmail())
					.province(requestDTO.getInformationOrder().getReceiverProvinceName())
					.district(requestDTO.getInformationOrder().getReceiverDistrictName())
					.ward(requestDTO.getInformationOrder().getReceiverWard())
					.postalCode(requestDTO.getInformationOrder().getReceiverPostalCode())
					.street(requestDTO.getInformationOrder().getReceiverStreet())
					.build();

			CreateReceiverWorkflow receiverWorkflow = workflowClient.newWorkflowStub(CreateReceiverWorkflow.class, options);

			String receiverId = receiverWorkflow.processCreateReceiver(customerId, request);

			log.debug("Logistic-Order-Service: Order-Service: Method-Create-receiver: Receiver created");

			return receiverId;
		} catch (WorkflowException exception) {
			log.error("Logistic-Order-Service: Order-Service: Method-Create-receiver: Workflow failed for request: {}", requestDTO, exception);
			throw new AppException(ErrorCode.WORKFLOW_FAILED);
		}
	}

	private Orders toOrder(String customerId, CreateOrderRequest requestDTO, ShippingMetadata shippingMetadata, PaymentMetadata paymentMetadata, String receiverId) {

		log.info("Logistic-Order-Service: Order-Service: Method-Create-Order: Request: {}, User: {}, Timestamp: {}", requestDTO, customerId, LocalDateTime.now());

		return Orders.builder()
				.customerId(customerId)

				.status(requestDTO.getOrderCreationStatus())
				.shipmentCode(requestDTO.getInformationOrder().getShipmentId())
				.note(requestDTO.getInformationOrder().getContentNote())
				.orderCode(requestDTO.getInformationOrder().getSaleOrderCode()) // check lai cho nay
				.moreRequire(requestDTO.getInformationOrder().getMoreRequire())

				.senderId(requestDTO.getInformationOrder().getSenderId())
				.senderName(requestDTO.getInformationOrder().getSenderName())

				.recipientName(requestDTO.getInformationOrder().getRecipientName())
				.recipientId(receiverId)

				.branchCode(requestDTO.getInformationOrder().getBranchCode())
				.serviceCode(requestDTO.getInformationOrder().getServiceCode())
				.receivingMethod(requestDTO.getInformationOrder().getReceivingMethod())

				.vehicle(requestDTO.getInformationOrder().getVehicle())
				.isBroken(requestDTO.getInformationOrder().isBroken())
				.deliveryRequire(requestDTO.getInformationOrder().getDeliveryRequire())

				.deliveryInstruction(requestDTO.getInformationOrder().getDeliveryInstruction())

				.weight(requestDTO.getInformationOrder().getWeight())
				.width(requestDTO.getInformationOrder().getWidth())
				.length(requestDTO.getInformationOrder().getLength())
				.height(requestDTO.getInformationOrder().getHeight())

				//				.pickupShipperId(requestDTO.getInformationOrder().getPickupShipperId())
				//				.deliveryShipperId(requestDTO.getInformationOrder().getDeliveryShipperId())

				.shippingMetadata(shippingMetadata)
				.paymentMetadata(paymentMetadata)

				.addOnServices(requestDTO.getInformationOrder().getAddOnServices())
				.build();
	}

	private PaymentMetadata toPaymentMetadata(CreateOrderRequest requestDTO) {
		log.debug("Logistic-Order-Service: Order-Service: Method-To-payment-metadata: {}", requestDTO);
		BigDecimal totalCost = calculateTotalCost(requestDTO);

		PaymentMetadata paymentMetadata = new PaymentMetadata();
		paymentMetadata.setCustomerCode(requestDTO.getCustomerCode());
		paymentMetadata.setTotalCost(totalCost); // tien van chuyen
		paymentMetadata.setPayer(requestDTO.getInformationOrder().getRecipientName());

		// Kiểm tra `codAmount`
		BigDecimal codAmount = requestDTO.getInformationOrder().getCodAmount();
		if (codAmount == null) {
			codAmount = BigDecimal.ZERO;
		}
		paymentMetadata.setCodAmount(codAmount);

		// Kiem tra thanh toan truoc hay sau
		PaymentType paymentType = requestDTO.getInformationOrder().getPaymentType();
		if (paymentType == null) {
			throw new IllegalArgumentException("PaymentType không được null.");
		}
		if (paymentType == PaymentType.POSTPAID) {
			// Thanh toan sau
			paymentMetadata.setPaymentType(PaymentType.POSTPAID);
			paymentMetadata.setPaymentStatus(PaymentStatus.UNPAID);
			paymentMetadata.setCodAmount(requestDTO.getInformationOrder().getCodAmount());
			BigDecimal totalAmount = totalCost.add(codAmount);
			paymentMetadata.setTotalAmount(totalAmount);
		} else {
			// Thanh toan truoc
			paymentMetadata.setPaymentType(PaymentType.PREPAID);
			paymentMetadata.setPaymentStatus(PaymentStatus.PAID);
			paymentMetadata.setTotalAmount(totalCost);
		}
		return paymentMetadata;
	}

	private ShippingMetadata toShippingMetadata(CreateOrderRequest requestDTO) {
		log.debug("Logistic-Order-Service: Order-Service: Method-To-shipping-metadata: {}", requestDTO);
		return ShippingMetadata.builder()
				.shippingMethod(requestDTO.getInformationOrder().getShippingMethod())
				// Khong handle o day
				// Tao method tinh di roi ghi vao day nha m goi sang service ben kia
				//.deliveredDate(requestDTO.getInformationOrder().getDeliveryTime())
				//.deliveryStatus(requestDTO.getInformationOrder().getDeliveryStatus())
				//.deliveryEstimateTime(requestDTO.getInformationOrder().getDeliveryTime())
				//.desiredDeliveryTime(requestDTO.getInformationOrder().getDeliveryTime())
				.deliveryRemarks(requestDTO.getInformationOrder().getDeliveryInstruction())
				.deliveryId(requestDTO.getInformationOrder().getShipmentId())
				.build();
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
					.subject("Dear " + customerInfResponse.getFullName() + " Your order id:" + orders.getId() + " have bean status update: " + orderStatus)
					.build();

			kafkaTemplate.send(KafkaTopic.UPDATE_ORDER, mailUpdateOrderStatus);

			ReceiverInfResponse receiverInfResponse = userServiceClient.getReceiverById(orders.getRecipientId()).getResult();
			MailUpdateOrderStatus mailUpdateOrderStatusReceiver = MailUpdateOrderStatus.builder()
					.to(receiverInfResponse.getEmail())
					.subject("Dear" + receiverInfResponse.getFullName() + "Your order have bean status update: " + orders.getOrderCode())
					.build();
			kafkaTemplate.send(KafkaTopic.UPDATE_ORDER, mailUpdateOrderStatusReceiver);

			log.info("Logistic-Order-Service: Order-Service: Method-Update-order-status: Order status updated have been sent to notification service");

			return true;
		} else {
			throw new AppException(ErrorCode.ORDER_STATUS_NOT_VALID);
		}
	}

	// xu ly tinh toan chi phi van chuyen
	private BigDecimal calculateTotalCost(CreateOrderRequest requestDTO) {
		log.debug("Logistic-Order-Service: Order-Service: Method-Calculate-total-cost: {}", requestDTO);

		ShippingRequestDTO shippingRequestDTO = ShippingRequestDTO.builder()
				.serviceType(requestDTO.getInformationOrder().getServiceCode())
				.shippingZone(requestDTO.getInformationOrder().getShippingZone())
				.weight(requestDTO.getInformationOrder().getWeight())
				.width(requestDTO.getInformationOrder().getWidth())
				.length(requestDTO.getInformationOrder().getLength())
				.height(requestDTO.getInformationOrder().getHeight())
				.build();

		// Tính toán chi phí vận chuyển
		BigDecimal shippingCost = shippingService.calculateShippingCost(shippingRequestDTO);
		return shippingCost;
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

	@Override
	public PaginatedResponse<OrderInfResponse> getOrderBySenderId(String senderId, int page, int size) {
		log.info("Logistic-Order-Service: Order-Service: Method-Get-order-by-sender-id: {}", senderId);

		Pageable pageable = PageRequest.of(page, size);
		Page<Orders> ordersPage = ordersRepository.findBySenderId(senderId, pageable);
		List<OrderInfResponse> orderInfResponses = ordersPage.getContent().stream().map(order -> {
			OrderInfResponse orderInfResponse = orderMapper.toOrderInfResponse(order);
			return setSenderField(order, orderInfResponse);
		}).toList();
		PaginatedResponse<OrderInfResponse> paginatedResponse = new PaginatedResponse<>();
		paginatedResponse.setContent(orderInfResponses);
		paginatedResponse.setPage(page);
		paginatedResponse.setSize(size);
		paginatedResponse.setTotalElements(ordersPage.getTotalElements());
		paginatedResponse.setTotalPages(ordersPage.getTotalPages());
		paginatedResponse.setLastPage(ordersPage.isLast());
		return paginatedResponse;
	}

	@Override
	public OrderInfResponse assignShipperPickUp(String orderId, String shipperId, PickupRequest pickupRequest) {

		Orders orderEntity = ordersRepository.findById(orderId).orElseThrow(() -> {
			log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Order not found");
			return new AppException(ErrorCode.ORDER_NOT_FOUND);
		});

		orderEntity.setPickupDate(pickupRequest.getPickupDate()); // Ngày pickup
		orderEntity.setPickupStatus("PENDING");  // Trạng thái pickup
		orderEntity.setPickupRemarks(pickupRequest.getRemarks());  // Ghi chú pickup

		// Check Receiving Method
		if (orderEntity.getReceivingMethod().equals(ReceivingMethod.CUSTOMER_ADDRESS)) {
			String existingShipperId = orderEntity.getPickupShipperId();
			if (existingShipperId != null && !existingShipperId.isEmpty()) {
				log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Shipper already assigned");
				throw new AppException(ErrorCode.SHIPPER_ALREADY_EXISTS);
			}

			// Assign Shipper
			ShipperInfResponse shipperInfResponse = assignShipper(shipperId);
			if (shipperInfResponse == null) {
				log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Shipper not found");
				throw new AppException(ErrorCode.SHIPPER_NOT_AVAILABLE);
			}
			orderEntity.setStatus(OrderStatus.PICKING);
			orderEntity.setPickupShipperId(shipperId);
			ordersRepository.save(orderEntity);
			log.info("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Shipper assigned successfully");

		} else {
			log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Invalid receiving method for this order");
			throw new AppException(ErrorCode.INVALID_REQUEST);
		}
		return orderMapper.toOrderInfResponse(orderEntity);
	}

	@Override
	public OrderInfResponse assignShipperDelivery(String orderId, String shipperId, DeliveryRequest deliveryRequest) {

		Orders orderEntity = ordersRepository.findById(orderId).orElseThrow(() -> {
			log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Order not found");
			return new AppException(ErrorCode.ORDER_NOT_FOUND);
		});

		ShippingMetadata shippingMetadata = orderEntity.getShippingMetadata();

		shippingMetadata.setDeliveredDate(deliveryRequest.getDeliveredDate()); // Ngày pickup
		shippingMetadata.setDeliveryStatus("PENDING");
		shippingMetadata.setDeliveryRemarks(deliveryRequest.getRemarks());

		// Check Receiving Method
		if (orderEntity.getReceivingMethod().equals(ReceivingMethod.CUSTOMER_ADDRESS)) {
			String existingShipperId = orderEntity.getPickupShipperId();
			if (existingShipperId != null && !existingShipperId.isEmpty()) {
				log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Shipper already assigned");
				throw new AppException(ErrorCode.SHIPPER_ALREADY_EXISTS);
			}

			// Assign Shipper
			ShipperInfResponse shipperInfResponse = assignShipper(shipperId);
			if (shipperInfResponse == null) {
				log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Shipper not found");
				throw new AppException(ErrorCode.SHIPPER_NOT_AVAILABLE);
			}
			orderEntity.setStatus(OrderStatus.DELIVERING);
			orderEntity.setDeliveryShipperId(shipperId);
			ordersRepository.save(orderEntity);
			log.info("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Shipper assigned successfully");

		} else {
			log.error("Logistic-Order-Service: Order-Service: Method-Assign-shipper-to-order: Invalid receiving method for this order");
			throw new AppException(ErrorCode.INVALID_REQUEST);
		}
		return orderMapper.toOrderInfResponse(orderEntity);
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


	private ShipperInfResponse assignShipper(String shipperId) {
		return userServiceClient
				.getShipperById(shipperId)
				.getResult();
	}
}
