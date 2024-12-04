package com.tdtu.logistics_orders_service.entity;

import com.tdtu.logistics_orders_service.enumrator.PaymentMethod;
import com.tdtu.logistics_orders_service.enumrator.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
//
//@Getter
//@Setter
//@Builder
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Entity(name = "payment")
//public class Payment extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
//
//    @NotBlank
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    PaymentStatus status;
//
//    @NotBlank
//    @Enumerated(EnumType.STRING)
//    @Column(name = "method")
//    PaymentMethod method;
//
//    @NotNull
//    BigDecimal amount; // Tổng tiền thanh toán
//
//    @NotNull
//    @Column(name = "transport_fee")
//    BigDecimal transportFee; // Phí vận chuyển
//
//    @NotNull
//    @Column(name = "total_fee")
//    BigDecimal totalFee; // Tổng cước phát sinh
//
//    @NotNull
//    @Column(name = "cash_on_delivery_amount")
//    BigDecimal cashOnDeliveryAmount; // Tiền thu hộ
//
//    @NotNull
//    @Column(name = "amount_to_receiver")
//    BigDecimal amountToReceiver; // Tổng tiền thu từ người nhận
//}
