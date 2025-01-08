package com.tdtu.logistics_users_service.activity;

import com.tdtu.common.dto.identity_service.CustomerRegisterAccountRequest;
import com.tdtu.common.orchestration.activity.RegistryAccountActivity;
import com.tdtu.logistics_users_service.dto.request.CreateCustomerRequest;
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
public class RegistryAccountActivityImpl implements RegistryAccountActivity {

    CustomerService customerService;

    @Override
    public void createAccount(CustomerRegisterAccountRequest request) {

        log.info("Logistics-Users-Service -> Registry-Account-Activity: Create-Account: Create account: {}", request.getUsername());

        customerService.createCustomer(CreateCustomerRequest.builder()
                    .email(request.getUsername())
                    .phoneNumber(request.getPhoneNumber())
                    .fullName(request.getFullName())
                    .build()
        );
    }
}
