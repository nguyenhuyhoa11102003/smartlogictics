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
public class UpdatePaymentStatusRequestDTO {
    @NotBlank
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    String updateBy;
}
