package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.dto.request.CreatePaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.EditPaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.PaymentInfResponseDTO;
import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import com.tdtu.logistics_orders_service.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class PaymentServiceImpl implements PaymentService {
//
//    PaymentRepository paymentRepository;
//
//    PaymentMapper paymentMapper;
//
//    @Override
//    public PaymentInfResponseDTO createPayment(CreatePaymentRequestDTO createPaymentRequestDTO) {
//        log.info("Payment Service: Creating Payment");
//
//        return paymentMapper.toPaymentInfResponseDTO(paymentRepository.save(paymentMapper.toPayment(createPaymentRequestDTO)));
//    }
//
//    @Override
//    public PaymentInfResponseDTO updatePaymentStatus(String id, String status) {
//
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
//
//        log.info("Payment Service: Updating Payment Status: {}", id);
//
//        return paymentMapper.toPaymentInfResponseDTO(paymentRepository.save(payment));
//    }
//
//    @Override
//    public PaymentInfResponseDTO editPayment(String id, EditPaymentRequestDTO editPaymentRequestDTO) {
//
//        if (!paymentRepository.existsById(id)) {
//            log.error("Payment Service: Edit Payment: Payment not found: {}", id);
//            throw new AppException(ErrorCode.PAYMENT_NOT_FOUND);
//        } else {
//            log.info("Payment Service: Editing Payment: {}", id);
//
//            Payment payment = paymentMapper.toPayment(editPaymentRequestDTO);
//            payment.setId(id);
//
//            return paymentMapper.toPaymentInfResponseDTO(paymentRepository.save(payment));
//        }
//    }
//
//    @Override
//    public PaymentInfResponseDTO getPaymentById(String id) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
//
//        log.info("Payment Service: Get Payment By Id: {}", id);
//
//        return paymentMapper.toPaymentInfResponseDTO(payment);
//    }
//
//}
