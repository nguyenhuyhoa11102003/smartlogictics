package com.tdtu.logistics_users_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdtu.logistics_users_service.enumrators.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCustomerRequest {

    private String phoneNumber;

    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Gender gender;
}