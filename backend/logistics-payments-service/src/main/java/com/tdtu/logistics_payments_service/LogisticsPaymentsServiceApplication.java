package com.tdtu.logistics_payments_service;

import com.tdtu.logistics_payments_service.config.ServiceUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(ServiceUrlConfig.class)
public class LogisticsPaymentsServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogisticsPaymentsServiceApplication.class, args);
	}
}
