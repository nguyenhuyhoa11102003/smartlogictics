package com.tdtu.logistics_api_gateway_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectTokenRequest {
    private String token;
}
