package com.tdtu.logistics_users_service.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ReceiverInfResponse {
    UUID id; // ID của người nhận (tự sinh).

    UUID customerId; // ID của khách hàng.

    String fullName; // Tên đầy đủ của người nhận.

    String phoneNumber; // Số điện thoại của người nhận.

    String email; // Email liên hệ.

    String province; // Tỉnh.

    String district; // Huyện.

    String ward; // Xã/Phường.

    String street; // Đường.

    String postalCode; // Mã bưu chính.
}
