package com.tdtu.logistics_users_service.orchestration.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.UserRegistrationActivity;
import com.tdtu.common.user_service.dto.CreateCustomerRequest;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
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
    public CustomerInfResponse createUserProfile(CustomerRegisterAccountRequest request) {

        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .fullName(request.getFullName())
                .build();

        CustomerInfResponse response = customerService.createCustomer(createCustomerRequest);

        log.info("Logistics-Users-Service -> Orchestration-Service -> Registry-Account-Activity: Create user profile: {}", request.getUsername());
        return response;
    }

    @Override
    public boolean rollbackCreateUserProfile(String userProfileId) {
        try {
            customerService.deleteCustomer(userProfileId);

            log.info("Logistics-Users-Service -> Orchestration-Service -> Registry-Account-Activity: Rollback create user profile: {}", userProfileId);

            return true;
        } catch (Exception e) {
            log.error("Logistics-Users-Service -> Orchestration-Service -> Registry-Account-Activity: Rollback create user profile failed: {}", userProfileId);

            return false;
        }
    }
}
