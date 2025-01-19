package com.tdtu.logistics_users_service.mapper;

import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateReceiverRequest;
import com.tdtu.common.user_service.ReceiverInfResponse;
import com.tdtu.logistics_users_service.entity.Receiver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiverMapper {

    Receiver toReceiver(CreateReceiverRequest createReceiverRequest);

    Receiver toReceiver(UpdateReceiverRequest updateReceiverRequest);

    ReceiverInfResponse toReceiverInfResponse(Receiver receiver);

}
