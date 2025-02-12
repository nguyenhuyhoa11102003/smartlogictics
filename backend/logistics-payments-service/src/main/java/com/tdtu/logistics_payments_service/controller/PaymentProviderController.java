package com.tdtu.logistics_payments_service.controller;

import com.tdtu.logistics_payments_service.dto.response.ApiResponse;
import com.tdtu.logistics_payments_service.service.implement.PaymentProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-providers")
public class PaymentProviderController {
    private final PaymentProviderService paymentProviderService;

    @GetMapping("/{id}/additional-settings")
    public ApiResponse<String> getAdditionalSettings(@PathVariable("id") String id) {
		return ApiResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.message("Get additional settings successfully")
				.result(null)
				.build();
	}
}