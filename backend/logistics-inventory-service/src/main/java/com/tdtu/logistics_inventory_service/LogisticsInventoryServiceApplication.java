package com.tdtu.logistics_inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tdtu.logistics_inventory_service")
public class LogisticsInventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogisticsInventoryServiceApplication.class, args);
	}
}