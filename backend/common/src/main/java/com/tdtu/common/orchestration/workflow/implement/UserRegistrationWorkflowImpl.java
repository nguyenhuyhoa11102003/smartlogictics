package com.tdtu.common.orchestration.workflow.implement;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.UserRegistrationActivity;
import com.tdtu.common.orchestration.workflow.UserRegistrationWorkflow;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class UserRegistrationWorkflowImpl implements UserRegistrationWorkflow {

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setInitialInterval(Duration.ofSeconds(1))
                    .setMaximumInterval(Duration.ofSeconds(10))
                    .setBackoffCoefficient(2)
                    .setMaximumAttempts(30)
                    .build())
            .build();

    @Override
    public CustomerInfResponse processRegistryAccount(CustomerRegisterAccountRequest request) {
        // Tạo activity stub trong phương thức workflow
        UserRegistrationActivity userRegistrationActivity = Workflow.newActivityStub(UserRegistrationActivity.class, defaultActivityOptions);

        try {
            CustomerInfResponse customerInfResponse = userRegistrationActivity.createUserProfile(request);
            return CustomerInfResponse.builder()
                    .id(customerInfResponse.getId())
                    .email(customerInfResponse.getEmail())
                    .fullName(customerInfResponse.getFullName())
                    .phoneNumber(customerInfResponse.getPhoneNumber())
                    .build();
        } catch (Exception e) {
            // Xử lý lỗi
            log.info("Error: {}", e.getMessage());
            return null;
        }
    }
}