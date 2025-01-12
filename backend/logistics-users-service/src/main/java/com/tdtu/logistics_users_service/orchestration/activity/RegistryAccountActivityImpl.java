package com.tdtu.logistics_users_service.orchestration.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.UserRegistrationActivity;
import com.tdtu.logistics_users_service.service.CustomerService;
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
public class RegistryAccountActivityImpl implements UserRegistrationActivity {

    CustomerService customerService;


    @Override
    public String createAccount(CustomerRegisterAccountRequest request) {
        return "";
    }

    @Override
    public String createUserProfile(CustomerRegisterAccountRequest request) {

        log.info("Den doan createUserProfile roi nhe");

        return "";
    }

    @Override
    public void linkAccountToUserProfile(String accountId, String userProfileId) {

    }

    @Override
    public void rollbackCreateAccount(String accountId) {

    }

    @Override
    public void rollbackCreateUserProfile(String userProfileId) {
        log.info("Den doan rollbackCreateUserProfile roi nhe");
    }
}
