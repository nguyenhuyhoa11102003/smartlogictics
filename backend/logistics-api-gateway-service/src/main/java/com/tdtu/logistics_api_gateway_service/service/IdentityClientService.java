package com.tdtu.logistics_api_gateway_service.service;


import com.tdtu.logistics_api_gateway_service.dto.request.IntrospectTokenRequest;
import com.tdtu.logistics_api_gateway_service.dto.response.IntrospectTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityClientService {

    private static final String URI_INTROSPECT = "http://localhost:8085/identity/api/v1/auth/introspect";

    public Mono<IntrospectTokenResponseDTO> introspectToken(IntrospectTokenRequest request) {

        String token = request.getToken();
        request.setToken(token.replace("Bearer ", "").trim());

        log.info("Introspect token...: {}", request.getToken());

        return WebClient.builder().build()
                .post()
                .uri(URI_INTROSPECT)
//                .header("Authorization", token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(IntrospectTokenResponseDTO.class);
    }

}
