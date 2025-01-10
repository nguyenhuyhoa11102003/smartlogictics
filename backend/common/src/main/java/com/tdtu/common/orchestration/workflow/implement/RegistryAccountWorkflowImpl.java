package com.tdtu.common.orchestration.workflow.implement;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.RegistryAccountActivity;
import com.tdtu.common.orchestration.workflow.RegistryAccountWorkflow;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class RegistryAccountWorkflowImpl implements RegistryAccountWorkflow {

    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(10))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(20)
            .build();

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryOptions)
            .build();

    private final RegistryAccountActivity registryAccount = Workflow.newActivityStub(RegistryAccountActivity.class, defaultActivityOptions);

    @Override
    public CustomerInfResponse processRegistryAccount(Object request) {
        registryAccount.createAccount((CustomerRegisterAccountRequest) request);
        return null;
    }
}
