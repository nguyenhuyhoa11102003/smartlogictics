package com.tdtu.common.orchestration.workflow.implement;

import com.tdtu.common.orchestration.activity.CreateReceiverActivity;
import com.tdtu.common.orchestration.workflow.CreateReceiverWorkflow;
import com.tdtu.common.user_service.dto.CreateReceiverRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class CreateReceiverWorkflowImpl implements CreateReceiverWorkflow {

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setInitialInterval(Duration.ofSeconds(1))
                    .setMaximumInterval(Duration.ofSeconds(5))
                    .setBackoffCoefficient(2)
                    .setMaximumAttempts(3)
                    .build())
            .build();

    @Override
    public String processCreateReceiver(String customerId, CreateReceiverRequest request) {
        CreateReceiverActivity createReceiverActivity = Workflow.newActivityStub(CreateReceiverActivity.class, defaultActivityOptions);

        try {

            return String.valueOf(createReceiverActivity.createReceiver(customerId, request));

        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());

            // Xu li rollback

            return "";
        }
    }
}
