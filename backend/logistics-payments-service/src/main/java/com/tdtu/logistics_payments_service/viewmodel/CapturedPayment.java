package com.tdtu.logistics_payments_service.viewmodel;


import com.tdtu.logistics_payments_service.model.Payment;
import com.tdtu.logistics_payments_service.model.enumeration.EPaymentMethod;
import com.tdtu.logistics_payments_service.model.enumeration.EPaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CapturedPayment(
        String orderId,
        BigDecimal amount,
        BigDecimal paymentFee,
        String gatewayTransactionId,
        EPaymentMethod paymentMethod,
        EPaymentStatus paymentStatus,
        String failureMessage ) {
    public static CapturedPayment fromModel(Payment payment){
        return CapturedPayment.builder()
                .amount(payment.getAmount())
                .paymentFee(payment.getPaymentFee())
                .orderId(payment.getOrderId())
                .gatewayTransactionId(payment.getGatewayTransactionId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .failureMessage(payment.getFailureMessage())
                .build();
    }

}