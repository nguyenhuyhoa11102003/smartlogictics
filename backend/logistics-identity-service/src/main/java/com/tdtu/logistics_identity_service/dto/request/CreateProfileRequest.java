package com.tdtu.logistics_identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdtu.logistics_identity_service.enumrator.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CreateProfileRequest {

    String fullName;

    //Check exist Email, sent OTP by Email Kafka
    String email;

    //Check exist Phone, sent OTP by SMS Kafka
    String phoneNumber;

    Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
    LocalDateTime dob;
}
