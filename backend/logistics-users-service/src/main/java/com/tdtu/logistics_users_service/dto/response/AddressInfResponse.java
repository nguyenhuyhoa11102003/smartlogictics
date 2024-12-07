package com.tdtu.logistics_users_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressInfResponse {
    private String userId;

    private String province; // Tỉnh.

    private String district; // Huyện.

    private String ward; // Xã/Phường.

    private String street;   // Đường.

    private String postalCode;

}
