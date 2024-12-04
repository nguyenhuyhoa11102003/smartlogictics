package com.tdtu.logistics_orders_service.mapper;

import com.tdtu.logistics_orders_service.dto.request.CreatePaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.EditPaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdatePaymentStatusRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.PaymentInfResponseDTO;
import com.tdtu.logistics_orders_service.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentInfResponseDTO toPaymentInfResponseDTO(Payment payment);

    Payment toPayment(CreatePaymentRequestDTO createPaymentRequestDTO);

    Payment toPayment(EditPaymentRequestDTO editPaymentRequestDTO);

    PaymentInfResponseDTO toPaymentInfResponseDTO(UpdatePaymentStatusRequestDTO updatePaymentStatusRequestDTO);

}
