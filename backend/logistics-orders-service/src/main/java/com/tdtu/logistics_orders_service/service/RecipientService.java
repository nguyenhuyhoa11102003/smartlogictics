package com.tdtu.logistics_orders_service.service;

import com.tdtu.logistics_orders_service.dto.request.CreateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.RecipientInfResponseDTO;

public interface RecipientService {

    RecipientInfResponseDTO createRecipient(CreateRecipientRequestDTO createRecipientRequestDTO);

    RecipientInfResponseDTO updateRecipient(String id, UpdateRecipientRequestDTO updateRecipientRequestDTO);

    RecipientInfResponseDTO getRecipientById(String id);

    RecipientInfResponseDTO getRecipientByEmail(String email);

    void deleteRecipientById(String id);

}
