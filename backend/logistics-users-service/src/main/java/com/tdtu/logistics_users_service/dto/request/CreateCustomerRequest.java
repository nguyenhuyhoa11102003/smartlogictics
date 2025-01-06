package com.tdtu.logistics_users_service.dto.request;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String email;

    private String phoneNumber;

    private String fullName;
}