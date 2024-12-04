package com.tdtu.logistics_orders_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tdtu.logistics_orders_service.entity.enumeration.EDeliveryMethod;
import com.tdtu.logistics_orders_service.entity.enumeration.EDeliveryStatus;
import com.tdtu.logistics_orders_service.entity.enumeration.EOrderStatus;
import com.tdtu.logistics_orders_service.entity.enumeration.EPaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String note;

	@Column(name = "shipment_code", unique = true, length = 13)
	String shipmentCode; // Mã vận đơn

	String description; // Mô tả đơn hàng

	@Column(name = "order_code")
	String trackingId; // Mã theo dõi

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
	private OrderAddress shippingAddressId; // Địa chỉ giao hàng

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private OrderAddress billingAddressId; // Địa chỉ thanh toán

	private String phoneNumber; // Số điện thoại
	private BigDecimal totalPrice; // Tổng giá trị đơn hàng
	private String senderId; // id người gửi

	//	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//	@JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
	//	Recipient recipient; // Liên kết đến người nhận

	//	@ManyToOne
	//	@JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
	//	Sender sender; // Liên kết đến người gửi


	//	@ManyToOne
	//	@JoinColumn(name = "shipper_id", referencedColumnName = "id")
	//	Shipper shipper; // Liên kết đến shipper

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	Set<OrderGood> orderGoods; // danh sach san pham

	private Long paymentId; // id cua payment

	@Enumerated(EnumType.STRING)
	private EOrderStatus orderStatus; // Trạng thái đơn hàng

	@Enumerated(EnumType.STRING)
	private EDeliveryMethod deliveryMethod; // Phương thức giao hàng

	@Enumerated(EnumType.STRING)
	private EDeliveryStatus deliveryStatus; // Trạng thái giao hàng

	@Enumerated(EnumType.STRING)
	private EPaymentStatus paymentStatus; //  Trạng thái thanh toán

	private String rejectReason; // Lý do từ chối


}
