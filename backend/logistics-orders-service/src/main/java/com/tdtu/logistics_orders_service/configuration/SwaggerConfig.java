package com.tdtu.logistics_orders_service.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Logistics Order Service API")
                        .version("1.0")
                        .description("Logistics Order Service API"));

//                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
//                .components(new Components().addSecuritySchemes("bearer-jwt",
//                        new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")
//                                .in(SecurityScheme.In.HEADER)
//                                .name("Authorization")))
//                ;
    }
}