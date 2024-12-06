package com.tdtu.logistics_identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdtu.logistics_identity_service.entity.Role;
import com.tdtu.logistics_identity_service.enumrator.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountRequest {

    @NotNull
    @Size(min = 6, max = 50, message = "invalid_username")
    String username;

    @NotNull
    @Size(min = 8, max = 50, message = "invalid_password")
    String password;

    @NotNull
    String fullName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "invalid_email")
    String email;

    @NotNull
    @Pattern(regexp = "^\\d{10,11}$", message = "invalid_phone_number")
    String phoneNumber;

    @NotNull
    Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
    LocalDateTime dob;

    Set<Role> roles;
}
