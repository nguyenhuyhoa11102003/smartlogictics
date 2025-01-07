package com.tdtu.logistics_identity_service.dto.response;

import com.tdtu.logistics_identity_service.enumrator.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountResponseDTO {
    String userId;

    String username;

    String fullName;

    String email;

    String phoneNumber;

    Gender gender;

    LocalDateTime dob;
}
