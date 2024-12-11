package com.tdtu.common.user_service.dto;

import com.tdtu.common.user_service.enums.Gender;
import com.tdtu.common.user_service.enums.UserStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerInfResponse {

    private String id;

    private String email;

    private String phoneNumber;

    private String fullName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String customerCode;

    private String loyaltyLevel;

    private UserStatus status;
}