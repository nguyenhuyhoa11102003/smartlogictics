package com.tdtu.logistics_payments_service.consumer;

import com.tdtu.common.dto.OrderEvent;
import com.tdtu.common.constant.KafkaTopic;
import com.tdtu.logistics_payments_service.model.Payment;
import com.tdtu.logistics_payments_service.model.enumeration.EPaymentMethod;
import com.tdtu.logistics_payments_service.model.enumeration.EPaymentStatus;
import com.tdtu.logistics_payments_service.service.implement.PaymentService;
import com.tdtu.logistics_payments_service.viewmodel.CapturedPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = KafkaTopic.CREATE_ORDER, groupId = "payment-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrder(@Payload OrderEvent orderEvent) {
        try {
            log.info("Payment-Service: Received Order: {}", orderEvent);

            // Call Payment Service to process payment
            CapturedPayment capturedPayment = CapturedPayment.builder()
                    .orderId(orderEvent.getId())
                    .paymentStatus(EPaymentStatus.PENDING)
                    .paymentFee(orderEvent.getTotalAmount())
                    .paymentMethod(EPaymentMethod.COD)
                    .amount(orderEvent.getTotalAmount())
                    .failureMessage("")
                    .gatewayTransactionId("")
                    .build();

            Payment payment =  this.paymentService.createPayment(capturedPayment);
            log.info("Payment-Service: Payment created: {}", payment);
        } catch (Exception e) {
            log.error("Payment-Service: Error processing order: {}", e.getMessage(), e);
        }
    }
}