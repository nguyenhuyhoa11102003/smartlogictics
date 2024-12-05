package com.tdtu.logistics_orders_service.dto.request;

import com.tdtu.logistics_orders_service.enumrator.PaymentMethod;
import com.tdtu.logistics_orders_service.enumrator.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequestDTO {
    @NotBlank
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @NotBlank
    @Enumerated(EnumType.STRING)
    PaymentMethod method;

    @NotNull
    BigDecimal amount; // Tổng tiền thanh toán

    @NotNull
    BigDecimal transportFee; // Phí vận chuyển

    @NotNull
    BigDecimal totalFee; // Tổng cước phát sinh

    @NotNull
    BigDecimal cashOnDeliveryAmount; // Tiền thu hộ

    @NotNull
    BigDecimal amountToReceiver;

}
