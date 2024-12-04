package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.request.CreatePaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.EditPaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.PaymentInfResponseDTO;

public interface PaymentService {

    PaymentInfResponseDTO createPayment(CreatePaymentRequestDTO createPaymentRequestDTO);

    PaymentInfResponseDTO updatePaymentStatus(String id, String status);

    PaymentInfResponseDTO editPayment(String id, EditPaymentRequestDTO editPaymentRequestDTO);

    PaymentInfResponseDTO getPaymentById(String id);

}
