package com.tdtu.logistics_identity_service.dto.response;

import com.tdtu.logistics_identity_service.enumrator.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProfileRespDTO {
    String profileId;

    String accountId;

    String fullName;

    //Check exist Email, sent OTP by Email Kafka
    String email;

    //Check exist Phone, sent OTP by SMS Kafka
    String phoneNumber;

    Gender gender;

    //Check valid >16 <100
//    LocalDate dob;
}
