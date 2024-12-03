package com.tdtu.logistics_orders_service.mapper;

import com.tdtu.logistics_orders_service.dto.request.CreateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.SenderInfResponseDTO;
import com.tdtu.logistics_orders_service.entity.Sender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SenderMapper {

    Sender toSender(CreateSenderRequestDTO requestDTO);

    SenderInfResponseDTO toSenderInfResponseDTO(Sender sender);

    Sender toSender(UpdateSenderRequestDTO requestDTO);

}
