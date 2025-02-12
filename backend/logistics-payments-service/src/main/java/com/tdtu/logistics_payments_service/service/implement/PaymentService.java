package com.tdtu.logistics_payments_service.service.implement;

import com.tdtu.logistics_payments_service.model.Payment;
import com.tdtu.logistics_payments_service.model.enumeration.EPaymentStatus;
import com.tdtu.logistics_payments_service.repository.PaymentRepository;
import com.tdtu.logistics_payments_service.viewmodel.CapturedPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public Payment createPayment(CapturedPayment completedPayment) {
        Payment payment =Payment.builder()
                .orderId(completedPayment.orderId())
                .paymentStatus(completedPayment.paymentStatus())
                .paymentFee(completedPayment.paymentFee())
                .paymentMethod(completedPayment.paymentMethod())
                .amount(completedPayment.amount())
                .failureMessage(completedPayment.failureMessage())
                .gatewayTransactionId(completedPayment.gatewayTransactionId())
                .build();
        return paymentRepository.save(payment);
    }

	public Payment updatePayment(Long paymentId, EPaymentStatus status) {
		CapturedPayment completedPayment = this.paymentRepository.findById(paymentId)
				.map(CapturedPayment::fromModel).orElseThrow();
		Payment payment =Payment.builder()
				.orderId(completedPayment.orderId())
				.paymentStatus(completedPayment.paymentStatus())
				.paymentFee(completedPayment.paymentFee())
				.paymentMethod(completedPayment.paymentMethod())
				.amount(completedPayment.amount())
				.failureMessage(completedPayment.failureMessage())
				.gatewayTransactionId(completedPayment.gatewayTransactionId())
				.paymentStatus(status)
				.build();
		return paymentRepository.save(payment);
	}

}
