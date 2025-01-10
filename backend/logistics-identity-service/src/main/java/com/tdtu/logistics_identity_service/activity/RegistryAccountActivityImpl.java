package com.tdtu.logistics_identity_service.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.RegistryAccountActivity;
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
public class RegistryAccountActivityImpl implements RegistryAccountActivity {

    @Override
    public String createAccount(CustomerRegisterAccountRequest request) {
        return "";
    }

    @Override
    public String createUserProfile(CustomerRegisterAccountRequest request) {
        return "";
    }

    @Override
    public void linkAccountToUserProfile(String acountId, String userProfileId) {

    }

    @Override
    public void rollbackCreateAccount(String accountId) {

    }

    @Override
    public void rollbackCreateUserProfile(String userProfileId) {

    }
}
