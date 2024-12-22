package com.tdtu.logistics_users_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAddressRequest {

    private String province; // Tỉnh.

    private String ProvinceCode;

    private String district; // Huyện.

    private String districtCode;

    private String ward;     // Xã/Phường.

    private String wardCode;

    private String street;   // Đường.

    private String postalCode;
}
