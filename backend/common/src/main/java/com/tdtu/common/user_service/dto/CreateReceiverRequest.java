package com.tdtu.common.user_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReceiverRequest {

    String fullName; // Tên đầy đủ của người nhận.

    String phoneNumber; // Số điện thoại của người nhận.

    String email; // Email liên hệ của người nhận.

    String province; // Tỉnh.

    String district; // Huyện.

    String ward; // Xã/Phường.

    String street; // Đường.

    String postalCode; // Mã bưu chính.

    String provinceCode; // Mã tỉnh/thành phố của người gửi

    String districtCode; // Mã quận/huyện của người gửi

    String communeCode; // Mã xã/phường của người gửi


}
