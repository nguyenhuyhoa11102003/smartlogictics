package com.tdtu.logistics_orders_service.entity;

import com.tdtu.logistics_orders_service.enumrator.PaymentStatus;
import com.tdtu.logistics_orders_service.enumrator.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payment_metadata")
public class PaymentMetadata {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; // ID của thông tin thanh toán

	@Column(name = "customer_code")
	String customerCode; // Mã khách hàng

	@Column(name = "total_cost")
	BigDecimal totalCost; // Tổng cước

	@Column(name = "cod_amount")
	BigDecimal codAmount; // Tiền thu hộ

	@Column(name = "payer")
	String payer; // Người trả cước

	// Mang sang day roi nha'
	@Column(name = "totalAmount")
	BigDecimal totalAmount; // Tổng giá trị đơn hàng

	@Column(name = "payment_status")
	@Enumerated(EnumType.STRING)
	PaymentStatus paymentStatus = PaymentStatus.UNPAID; // Trạng thái thanh toán

	@Column(name = "payment_type", nullable = false)
	@Enumerated(EnumType.STRING)
	PaymentType paymentType = PaymentType.PREPAID; // Loại thanh toán (PREPAID, POSTPAID)

	@Column(name = "payment_method")
	String paymentMethod; // Phương thức thanh toán

	@Column(name = "payment_time")
	String paymentTime; // Thời gian thanh toán

	@Column(name = "payment_remarks")
	String paymentRemarks; // Ghi chú thanh toán

	@Column(name = "payment_id")
	String paymentId; // Liên kết đến thông tin thanh toán

	@Column(name = "payment_code")
	String paymentCode; // Lien ket voi Payment-Service

}