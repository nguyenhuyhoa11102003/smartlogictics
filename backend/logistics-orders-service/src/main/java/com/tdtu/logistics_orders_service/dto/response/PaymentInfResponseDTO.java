package com.tdtu.logistics_orders_service.dto.response;

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
public class PaymentInfResponseDTO {
    String id;

    PaymentStatus status;

    PaymentMethod method;

    BigDecimal amount; // Tổng tiền thanh toán

    BigDecimal transportFee; // Phí vận chuyển

    BigDecimal totalFee; // Tổng cước phát sinh

    BigDecimal cashOnDeliveryAmount; // Tiền thu hộ

    BigDecimal amountToReceiver;

}
