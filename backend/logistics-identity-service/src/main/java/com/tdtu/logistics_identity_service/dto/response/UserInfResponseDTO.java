package com.tdtu.logistics_identity_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tdtu.logistics_identity_service.enumrator.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfResponseDTO {

    String accountId;

    String profileId;

    String fullName;

    String email;

    String phoneNumber;

    Gender gender;

    String role;
}
