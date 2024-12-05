package com.tdtu.logistics_orders_service.mapper;

import com.tdtu.logistics_orders_service.dto.request.CreateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.RecipientInfResponseDTO;
import com.tdtu.logistics_orders_service.entity.Recipient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipientMapper {

    Recipient toRecipient(CreateRecipientRequestDTO requestDTO);

    Recipient toRecipient(UpdateRecipientRequestDTO requestDTO);

    RecipientInfResponseDTO toRecipientInfResponseDTO(Recipient recipient);
}
