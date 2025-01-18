package com.tdtu.logistics_users_service.service;

import com.tdtu.logistics_users_service.dto.model.SenderDetailDTO;
import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SenderService {

    SenderInfResponse createSender(CreateSenderRequest createSenderRequest);

    SenderDetailDTO getSenderById(String id);

    SenderInfResponse updateSender(String id, UpdateSenderRequest updateSenderRequest);

    Page<SenderDetailDTO> searchByCustomerIdDto(String customerId, Pageable pageable);
}
