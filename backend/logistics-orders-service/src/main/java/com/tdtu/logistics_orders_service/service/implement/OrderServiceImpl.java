package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.entity.Order;
import com.tdtu.logistics_orders_service.entity.OrderAddress;
import com.tdtu.logistics_orders_service.entity.OrderGood;
import com.tdtu.logistics_orders_service.entity.enumeration.EDeliveryStatus;
import com.tdtu.logistics_orders_service.repository.OrderGoodRepository;
import com.tdtu.logistics_orders_service.repository.OrderRepository;
import com.tdtu.logistics_orders_service.service.OrderService;
import com.tdtu.logistics_orders_service.viewmodel.order.OrderPostVm;
import com.tdtu.logistics_orders_service.viewmodel.order.OrderVm;
import com.tdtu.logistics_orders_service.viewmodel.order.OrderAddressPostVm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
	OrderRepository orderRepository;
	OrderGoodRepository orderGoodRepository;

	@Override
	public OrderVm createOrder(OrderPostVm orderPostVm) {
		// địa chỉ thanh toán đơn hàng
		OrderAddressPostVm billingAddressPostVm = orderPostVm.billingAddressPostVm();
		OrderAddress billOrderAddress = OrderAddress.builder()
				.phone(billingAddressPostVm.phone())
				.contactName(billingAddressPostVm.contactName())
				.addressLine1(billingAddressPostVm.addressLine1())
				.addressLine2(billingAddressPostVm.addressLine2())
				.city(billingAddressPostVm.city())
				.zipCode(billingAddressPostVm.zipCode())
				.districtId(billingAddressPostVm.districtId())
				.districtName(billingAddressPostVm.districtName())
				.stateOrProvinceId(billingAddressPostVm.stateOrProvinceId())
				.stateOrProvinceName(billingAddressPostVm.stateOrProvinceName())
				.countryId(billingAddressPostVm.countryId())
				.countryName(billingAddressPostVm.countryName())
				.build();

		// địa chỉ giao hàng đơn hàng
		OrderAddressPostVm shipOrderAddressPostVm = orderPostVm.shippingAddressPostVm();
		OrderAddress shippOrderAddress = OrderAddress.builder()
				.phone(shipOrderAddressPostVm.phone())
				.contactName(shipOrderAddressPostVm.contactName())
				.addressLine1(shipOrderAddressPostVm.addressLine1())
				.addressLine2(shipOrderAddressPostVm.addressLine2())
				.city(shipOrderAddressPostVm.city())
				.zipCode(shipOrderAddressPostVm.zipCode())
				.districtId(shipOrderAddressPostVm.districtId())
				.districtName(shipOrderAddressPostVm.districtName())
				.stateOrProvinceId(shipOrderAddressPostVm.stateOrProvinceId())
				.stateOrProvinceName(shipOrderAddressPostVm.stateOrProvinceName())
				.countryId(shipOrderAddressPostVm.countryId())
				.countryName(shipOrderAddressPostVm.countryName())
				.build();

		Order order = Order.builder()
				.shippingAddressId(shippOrderAddress)
				.billingAddressId(billOrderAddress)
				.note(orderPostVm.note())
				.totalPrice(orderPostVm.totalPrice())
				.phoneNumber(orderPostVm.phoneNumber())
				.deliveryMethod(orderPostVm.deliveryMethod())
				.deliveryStatus(EDeliveryStatus.PREPARING)
				.paymentStatus(orderPostVm.paymentStatus())
				.build();

		Set<OrderGood> orderGoods = orderPostVm.orderItemPostVms().stream()
				.map(item -> OrderGood.builder()
						.quantity(item.quantity())
						.order(order)
						.build())
				.collect(Collectors.toSet());
		this.orderGoodRepository.saveAll(orderGoods);
		order.setOrderGoods(orderGoods);
		orderRepository.save(order);
		return null;
	}
}
