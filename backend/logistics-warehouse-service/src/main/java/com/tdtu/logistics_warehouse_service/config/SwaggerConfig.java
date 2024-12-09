package com.tdtu.logistics_warehouse_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI()
				.info(new Info()
						.title("Logistics Identity Service API")
						.version("1.0")
						.description("Logistics Identity Service API"))
				.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearer-jwt"))
				.components(new Components().addSecuritySchemes("bearer-jwt",
						new io.swagger.v3.oas.models.security.SecurityScheme()
								.type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.in(SecurityScheme.In.HEADER)
								.name("Authorization")))
				;
	}
}

