package com.tdtu.logistics_users_service.mapper;

import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import com.tdtu.logistics_users_service.entity.Sender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SenderMapper {

    Sender toEntity(CreateSenderRequest request);

    Sender toEntity(UpdateSenderRequest request);

    SenderInfResponse toResponse(Sender sender);
}
