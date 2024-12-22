package com.tdtu.logistics_identity_service.configuration;

import com.tdtu.logistics_identity_service.constant.PredefinedRole;
import com.tdtu.logistics_identity_service.entity.Account;
import com.tdtu.logistics_identity_service.entity.Role;
import com.tdtu.logistics_identity_service.repository.RoleRepository;
import com.tdtu.logistics_identity_service.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserAccountRepository userAccountRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");

        return args -> {
            if (userAccountRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {

                Role customerRole = Role.builder()
                        .name(PredefinedRole.CUSTOMER_ROLE)
                        .description("Customer role")
                        .build();

                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                Role shipperRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.SHIPPER_ROLE)
                        .description("Shipper role")
                        .build());

                roleRepository.saveAll(Set.of(customerRole, adminRole, shipperRole));

                var roles = new HashSet<Role>();
                roles.add(adminRole);
                roles.add(shipperRole);

                Account account = Account.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userAccountRepository.save(account);
                log.warn("Admin user has been created with default password: '{}', please change it", ADMIN_PASSWORD);
            }
            log.info("Application initialization completed.");
        };
    }
}
