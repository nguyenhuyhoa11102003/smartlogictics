package com.tdtu.logistics_identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfResponseDTO {

    String id;

    String username;

    String profileId;

    Instant createdDate;

    Instant lastModifiedDate;

    Set<String> roles;
}
