package com.tdtu.logistics_users_service.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    private String email;

    private String phoneNumber;

    private String fullName;
}