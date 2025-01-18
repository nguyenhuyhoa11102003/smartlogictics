package com.tdtu.logistics_users_service.service;

import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;

public interface SenderService {

    SenderInfResponse createSender(CreateSenderRequest createSenderRequest);

    SenderInfResponse updateSender(String senderId, UpdateSenderRequest updateSenderRequest);

}
