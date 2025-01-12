package com.tdtu.common.orchestration.workflow.implement;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.UserRegistrationActivity;
import com.tdtu.common.orchestration.workflow.UserRegistrationWorkflow;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

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

        String accountId = userRegistrationActivity.createAccount(request);
        String userProfileId = userRegistrationActivity.createUserProfile(request);

        userRegistrationActivity.linkAccountToUserProfile(accountId, userProfileId);

        return null; // Thay đổi để trả về thông tin người dùng hợp lệ
    }
}