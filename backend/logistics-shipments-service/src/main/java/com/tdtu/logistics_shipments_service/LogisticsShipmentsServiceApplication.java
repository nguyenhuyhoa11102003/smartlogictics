package com.tdtu.logistics_shipments_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LogisticsShipmentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsShipmentsServiceApplication.class, args);
	}

}