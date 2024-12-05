package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.request.CreateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.SenderInfResponseDTO;

public interface SenderService {
    SenderInfResponseDTO createSender(CreateSenderRequestDTO createSenderRequestDTO);

    SenderInfResponseDTO updateSender(String id, UpdateSenderRequestDTO updateSenderRequestDTO);

    SenderInfResponseDTO getSenderById(String id);

    SenderInfResponseDTO getSenderByEmail(String email);

    void deleteSenderById(String id);
}
