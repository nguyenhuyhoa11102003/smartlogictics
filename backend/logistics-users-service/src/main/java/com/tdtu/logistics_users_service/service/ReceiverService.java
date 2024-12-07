package com.tdtu.logistics_users_service.service;


import com.tdtu.logistics_users_service.dto.request.CreateReceiverRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateReceiverRequest;
import com.tdtu.logistics_users_service.dto.response.ReceiverInfResponse;

import java.util.List;

public interface ReceiverService {

    ReceiverInfResponse createReceiver(CreateReceiverRequest createReceiverRequest);

    ReceiverInfResponse getReceiverById(String id);

    List<ReceiverInfResponse> getAllReceiversByCustomerId(String customerId);

    ReceiverInfResponse updateReceiver(String id, UpdateReceiverRequest updateReceiverRequest);
}