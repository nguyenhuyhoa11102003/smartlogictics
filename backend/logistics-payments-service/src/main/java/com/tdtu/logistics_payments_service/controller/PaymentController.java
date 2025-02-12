package com.tdtu.logistics_payments_service.controller;

import com.tdtu.logistics_payments_service.model.enumeration.EPaymentStatus;
import com.tdtu.logistics_payments_service.service.implement.PaymentService;
import com.tdtu.logistics_payments_service.viewmodel.CapturedPayment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/capture")
    public ResponseEntity<?> capturePayment(@Valid @RequestBody CapturedPayment capturedPayment) {
		paymentService.createPayment(capturedPayment);
        return ResponseEntity.ok("Payment captured successfully");
    }

	@PostMapping("/refund")
	public ResponseEntity<?> refundPayment(@Valid @RequestBody CapturedPayment capturedPayment) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/cancel")
	public ResponseEntity<?> cancelPayment(@Valid @RequestBody CapturedPayment capturedPayment) {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/update/status/{paymentId}/{status}")
	public ResponseEntity<?> updatePayment(@PathVariable("paymentId") Long paymentId,@PathVariable("status") EPaymentStatus status) {
		paymentService.updatePayment(paymentId, status);
		return ResponseEntity.ok("Payment updated successfully");
	}

}