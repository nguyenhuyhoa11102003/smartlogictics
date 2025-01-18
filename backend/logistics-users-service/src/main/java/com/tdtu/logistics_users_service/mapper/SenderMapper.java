package com.tdtu.logistics_users_service.mapper;

import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import com.tdtu.logistics_users_service.entity.Sender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SenderMapper {

    @Mapping(target = "fullName", source = "senderName")
    @Mapping(target = "phoneNumber", source = "senderPhone")
    @Mapping(target = "email", source = "senderMail")
    Sender toEntity(CreateSenderRequest request);

    @Mapping(target = "fullName", source = "senderName")
    @Mapping(target = "phoneNumber", source = "senderPhone")
    @Mapping(target = "email", source = "senderMail")
    Sender toEntity(UpdateSenderRequest request);

    SenderInfResponse toResponse(Sender sender);
}
