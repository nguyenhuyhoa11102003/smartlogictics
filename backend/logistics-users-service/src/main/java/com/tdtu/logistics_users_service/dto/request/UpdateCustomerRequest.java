package com.tdtu.logistics_users_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdtu.logistics_users_service.enumrators.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerRequest {

    String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    String identityCard;

    UpdateAddressRequest address;

    Gender gender;
}