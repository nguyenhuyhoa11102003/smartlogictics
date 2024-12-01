package com.tdtu.logistics_identity_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangesPasswordRequest {
    @NotNull
    @Size(min = 6, max = 50, message = "invalid_username")
    String username;

    @NotNull
    @Size(min = 8, max = 50, message = "invalid_password")
    String currentPassword;

    @NotNull
    @Size(min = 8, max = 50, message = "invalid_password")
    String newPassword;
}
