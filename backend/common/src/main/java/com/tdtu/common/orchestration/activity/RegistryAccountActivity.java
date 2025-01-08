package com.tdtu.common.orchestration.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface RegistryAccountActivity {

    @ActivityMethod
    void createAccount(CustomerRegisterAccountRequest request);
}
