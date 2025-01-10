package com.tdtu.common.orchestration.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface RegistryAccountActivity {

    @ActivityMethod
    String createAccount(CustomerRegisterAccountRequest request);

    @ActivityMethod
    String createUserProfile(CustomerRegisterAccountRequest request);

    @ActivityMethod
    void linkAccountToUserProfile(String acountId, String userProfileId);

    @ActivityMethod
    void rollbackCreateAccount(String accountId);

    @ActivityMethod
    void rollbackCreateUserProfile(String userProfileId);
}