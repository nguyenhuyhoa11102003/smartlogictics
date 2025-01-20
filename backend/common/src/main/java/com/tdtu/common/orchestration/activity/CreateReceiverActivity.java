package com.tdtu.common.orchestration.activity;

import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface CreateReceiverActivity {

    @ActivityMethod
    String createReceiver(String customerId, CreateReceiverRequest createReceiverRequest);

    @ActivityMethod
    boolean rollbackCreateReceiver(String receiverId);
}
