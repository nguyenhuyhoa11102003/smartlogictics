package com.tdtu.logistics_users_service.dto.request;

public record UpdateSenderRequest(
        String senderName,        // Tên người gửi
        String senderPhone,       // Số điện thoại người gửi
        String senderMail,        // Email của người gửi
        String senderAddress,     // Địa chỉ người gửi
        String senderProvinceCode, // Mã tỉnh/thành phố của người gửi
        String senderProvinceName, // Tên tỉnh/thành phố của người gửi
        String senderDistrictCode, // Mã quận/huyện của người gửi
        String senderDistrictName, // Tên quận/huyện của người gửi
        String senderCommuneCode,  // Mã xã/phường của người gửi
        String senderCommuneName,  // Tên xã/phường của người gửi
        String senderPostalCode     // Mã bưu chính của người gửi
) {}