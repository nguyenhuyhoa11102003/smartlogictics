package com.tdtu.common.orchestration.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface UserRegistrationActivity {

    @ActivityMethod
    CustomerInfResponse createUserProfile(CustomerRegisterAccountRequest request);

    @ActivityMethod
    boolean rollbackCreateUserProfile(String userProfileId);
}