package com.tdtu.logistics_orders_service.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public SpringSecurityAuditorAware auditorAwareImpl() {
        return new SpringSecurityAuditorAware();
    }
}

