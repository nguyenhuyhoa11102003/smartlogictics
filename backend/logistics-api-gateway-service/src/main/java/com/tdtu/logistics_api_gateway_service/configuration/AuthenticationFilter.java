package com.tdtu.logistics_api_gateway_service.configuration;


import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdtu.logistics_api_gateway_service.dto.request.IntrospectTokenRequest;
import com.tdtu.logistics_api_gateway_service.dto.response.ApiResponse;
import com.tdtu.logistics_api_gateway_service.service.IdentityClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;

    private final IdentityClientService identityClientService;

    private final String[] AUTH_WHITELIST = {
            "/identity/api/v1/auth/login",
            "/identity/api/v1/account/create",
            "/identity/api/v1/auth/refresh",
            "/identity/swagger-ui/**",
            "/identity/swagger-ui-custom.html",
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("AuthenticationFilter....");

        if (isPublicUrl(exchange.getRequest())) {
            log.info("Public URL..., skip authentication");
            return chain.filter(exchange);
        }

        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtil.isNullOrEmpty(bearerToken)) {
            return unauthenticated(exchange.getResponse());
        }

        log.debug("Bearer token: {}", bearerToken);

        return identityClientService.introspectToken(new IntrospectTokenRequest(bearerToken))
                .flatMap(introspectTokenResponseDTO -> {
                    if (introspectTokenResponseDTO.isActive()) {
                        log.debug("Token is active..., result: {}", introspectTokenResponseDTO);
                        return chain.filter(exchange);
                    } else {
                        log.debug("Token is not active..., result: {}", introspectTokenResponseDTO);
                        return unauthenticated(exchange.getResponse());
                    }
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .isSuccess(false)
                .message("Unauthorized")
                .timestamp(LocalDate.from(LocalDateTime.now()))
                .build();

        try {

            return response.writeWith(Mono.just(
                    response.bufferFactory().wrap(
                            objectMapper.writeValueAsBytes(apiResponse)
                    )
            )).doOnError(e -> log.error("Error processing JSON response", e));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPublicUrl(ServerHttpRequest request) {
        String requestPath = request.getURI().getPath();
        return Arrays.stream(AUTH_WHITELIST)
                .anyMatch(requestPath::startsWith);
    }

}
