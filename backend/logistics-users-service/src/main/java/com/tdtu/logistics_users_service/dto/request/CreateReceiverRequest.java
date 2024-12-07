package com.tdtu.logistics_users_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReceiverRequest {

    String customerId; // Id của khách hàng.

    String fullName; // Tên đầy đủ của người nhận.

    String phoneNumber; // Số điện thoại của người nhận.

    String email; // Email liên hệ của người nhận.

    String province; // Tỉnh.

    String district; // Huyện.

    String ward; // Xã/Phường.

    String street; // Đường.

    String postalCode; // Mã bưu chính.
}
