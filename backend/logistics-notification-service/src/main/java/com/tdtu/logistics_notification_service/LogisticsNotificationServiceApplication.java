package com.tdtu.logistics_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.tdtu.logistics_notification_service.repository.httpClient")
public class LogisticsNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsNotificationServiceApplication.class, args);
	}

}
