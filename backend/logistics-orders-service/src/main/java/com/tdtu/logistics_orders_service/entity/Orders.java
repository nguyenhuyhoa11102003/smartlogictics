package com.tdtu.logistics_orders_service.entity;

import com.tdtu.common.orders_service.enums.AddOnService;
import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import com.tdtu.logistics_orders_service.enumrator.ReceivingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id; // UUID của đơn hàng

	@Enumerated(EnumType.STRING)
	OrderStatus status; // Trạng thái đơn hàng

	@Column(name = "shipment_code", unique = true, length = 13)
	String shipmentCode; // Mã vận đơn

	@Column(name = "order_code")
	String orderCode; // Mã đơn hàng bán

	@Column(name = "note")
	String note; // Ghi chú

	@Column(name = "more_require")
	String moreRequire; // Yêu cầu bổ sung

	@Column(name = "customer_id")
	String customerId; // Liên kết đến nguoi tao don dang

	@Column(name = "recipient_name")
	String recipientName; // Liên kết đến người nhận

	@Column(name = "recipient_id")
	String recipientId; // Liên kết đến người nhận

	@Column(name = "sender_name")
	String senderName; // Tên người gửi

	@Column(name = "sender_id")
	String senderId; // Liên kết đến người gửi

	@Column(name = "branch_code")
	String branchCode; // Mã chi nhánh warehouse

	@Enumerated(EnumType.STRING)
	@Column(name = "service_code")
	DeliveryServiceType serviceCode; // Mã dịch vụ giao hàng

	@Enumerated(EnumType.STRING)
	@Column(name = "receiving_method")
	ReceivingMethod receivingMethod; // Phương thức nhận hàng

	@Column(name = "vehicle")
	String vehicle; // Loại phương tiện vận chuyển

	@Column(name = "is_broken")
	boolean isBroken; // Hàng hóa có bị hư hỏng không

	@Column(name = "delivery_require")
	String deliveryRequire; // Yêu cầu giao hàng

	@Column(name = "delivery_instruction")
	String deliveryInstruction; // Hướng dẫn giao hàng

	@Column(name = "weight")
	double weight; // Trọng lượng đơn vị hàng (gram)

	@Column(name = "width")
	double width; // Chiều rộng của hàng hóa

	@Column(name = "length")
	double length; // Chiều dài của hàng hóa

	@Column(name = "height")
	double height; // Chiều cao của hàng hóa

	@Column(name = "pickup_shipper_id")
	String pickupShipperId; // Thông tin shipper nhận đơn hàng

	@Column(name = "delivery_shipper_id")
	String deliveryShipperId; // Shipper giao hàng

// Chuyen collunm nay sang payment_metadata roi nha fen:
// Sang entity payment_metadata la co
//	@Column(name = "totalAmount")
//	BigDecimal totalAmount; // Tổng giá trị đơn hàng

	@ElementCollection
	@Enumerated(EnumType.STRING)
	@Column(name = "add_on_services")
	List<AddOnService> addOnServices;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_metadata_id", referencedColumnName = "id") // Khóa ngoại trong bảng Orders
	ShippingMetadata shippingMetadata; // Liên kết đến dịch vụ vận chuyển

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_metadata_id") // Thêm cột để liên kết
	PaymentMetadata paymentMetadata; // Thông tin thanh toán

	private LocalDateTime pickupDate;  // Thời gian pickup
	private String pickupStatus;  // Trạng thái pickup (PENDING, COMPLETED, FAILED)
	private String pickupRemarks;  // Ghi chú pick

	// Chuyen deliveredDate sang bang ShippingMetadata roi nha'
	// private LocalDateTime deliveredDate;  // Thời gian giao hàng
	// private String deliveryStatus;  // Trạng thái giao hàng (DELIVERED, FAILED)
	// private String deliveryRemarks;  // Ghi chú giao hàng

	@LastModifiedBy
	String createBy;
}