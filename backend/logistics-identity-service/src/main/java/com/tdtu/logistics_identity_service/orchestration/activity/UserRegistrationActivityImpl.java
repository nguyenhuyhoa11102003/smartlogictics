package com.tdtu.logistics_identity_service.orchestration.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.UserRegistrationActivity;
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
public class UserRegistrationActivityImpl implements UserRegistrationActivity {

    @Override
    public String createAccount(CustomerRegisterAccountRequest request) {

        log.info("Den doan createAccount roi nhe");

        return "";
    }

    @Override
    public String createUserProfile(CustomerRegisterAccountRequest request) {

        return "";
    }

    @Override
    public void linkAccountToUserProfile(String acountId, String userProfileId) {
        log.info("Den doan linkAccountToUserProfile roi nhe");
    }

    @Override
    public void rollbackCreateAccount(String accountId) {
        log.info("Den doan rollbackCreateAccount roi nhe");
    }

    @Override
    public void rollbackCreateUserProfile(String userProfileId) {

    }

}
