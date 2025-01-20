package com.tdtu.logistics_orders_service.entity;

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

    @Column(name = "payment_code")
    String paymentCode; // Lien ket voi Payment-Service
}