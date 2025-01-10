package com.tdtu.common.user_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdtu.common.user_service.enums.Gender;
import com.tdtu.common.user_service.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
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

    private String address;
}