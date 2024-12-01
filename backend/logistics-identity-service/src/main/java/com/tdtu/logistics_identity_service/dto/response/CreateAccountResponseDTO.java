package com.tdtu.logistics_identity_service.dto.response;

import com.tdtu.logistics_identity_service.enumrator.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountResponseDTO {
    String accountId;

    String id;

    String username;

    String fullName;

    String email;

    String phoneNumber;

    Gender gender;

    LocalDate dob;

    Instant createdDate;

    Instant lastModifiedDate;

    String lastModifiedBy;

    //Add-Permission
}
