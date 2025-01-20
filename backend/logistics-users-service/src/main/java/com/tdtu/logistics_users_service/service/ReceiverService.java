package com.tdtu.logistics_users_service.service;


import com.tdtu.logistics_users_service.dto.model.ReceiverDetailDTO;
import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateReceiverRequest;
import com.tdtu.common.user_service.ReceiverInfResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceiverService {

    ReceiverInfResponse createReceiver(CreateReceiverRequest createReceiverRequest);

    String createReceiver(String customerId, CreateReceiverRequest createReceiverRequest);

    ReceiverDetailDTO getReceiverById(String id);

    List<ReceiverInfResponse> getAllReceiversByCustomerId(String customerId);

    ReceiverInfResponse updateReceiver(String id, UpdateReceiverRequest updateReceiverRequest);

    Page<ReceiverDetailDTO> searchByCustomerIdDto(String customerId, Pageable pageable);

    void deleteReceiver(String id);
}