package com.tdtu.logistics_identity_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRegisterAccountRequest {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "invalid_email")
    String username;

    @NotNull
    @Size(min = 8, max = 50, message = "invalid_password")
    String password;

    @NotNull
    String fullName;

    @NotNull
    @Pattern(regexp = "^\\d{10,11}$", message = "invalid_phone_number")
    String phoneNumber;
}
