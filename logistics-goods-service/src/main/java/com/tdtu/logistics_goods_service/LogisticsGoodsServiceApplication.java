package com.tdtu.logistics_goods_service;

import com.tdtu.logistics_goods_service.config.ServiceUrlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(ServiceUrlConfig.class)
public class LogisticsGoodsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsGoodsServiceApplication.class, args);
	}

}
