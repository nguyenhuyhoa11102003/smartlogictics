package com.tdtu.logistics_users_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdtu.logistics_users_service.enumrators.Gender;
import com.tdtu.logistics_users_service.enumrators.UserStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerInfResponse {

    private String id;

    private String email;

    private String phoneNumber;

    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Gender gender;

    private String customerCode;

    private String loyaltyLevel;

    private UserStatus status;
}