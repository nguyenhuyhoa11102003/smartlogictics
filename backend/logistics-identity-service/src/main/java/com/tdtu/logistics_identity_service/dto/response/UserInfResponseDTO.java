package com.tdtu.logistics_identity_service.dto.response;

import com.tdtu.logistics_identity_service.enumrator.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfResponseDTO {

    String accountId;

    String username;

    String fullName;

    String email;

    String phoneNumber;

    Gender gender;
}
