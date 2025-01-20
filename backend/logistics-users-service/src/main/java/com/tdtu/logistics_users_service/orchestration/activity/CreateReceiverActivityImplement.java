package com.tdtu.logistics_users_service.orchestration.activity;

import com.tdtu.common.orchestration.activity.CreateReceiverActivity;
import com.tdtu.common.user_service.ReceiverInfResponse;
import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import com.tdtu.logistics_users_service.service.ReceiverService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CreateReceiverActivityImplement implements CreateReceiverActivity {

    ReceiverService receiverService;

    @Override
    public String createReceiver(String customerId, CreateReceiverRequest createReceiverRequest) {

        String receiverId = receiverService.createReceiver(customerId, createReceiverRequest);

        log.info("Logistics-Users-Service -> Orchestration-Service -> Create-Receiver-Activity: Create receiver: {}", receiverId);

        return receiverId;
    }

    @Override
    public boolean rollbackCreateReceiver(String receiverId) {

        try {
            receiverService.deleteReceiver(receiverId);

            log.info("Logistics-Users-Service -> Orchestration-Service -> Create-Receiver-Activity: Rollback create receiver: {}", receiverId);
            return true;
        } catch (Exception e) {
            log.error("Logistics-Users-Service -> Orchestration-Service -> Create-Receiver-Activity: Rollback create receiver failed: {}", receiverId);

            return false;
        }
    }

}
