package com.tdtu.common.user_service.dto;

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