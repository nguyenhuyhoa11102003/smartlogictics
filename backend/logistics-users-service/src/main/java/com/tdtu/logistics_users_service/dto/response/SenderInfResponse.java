package com.tdtu.logistics_users_service.dto.response;

public record SenderInfResponse(
        String id,
        String customerId,
        String fullName,
        String phoneNumber,
        String email,
        String province,
        String senderProvinceCode,
        String district,
        String senderDistrictCode,
        String ward,
        String senderCommuneCode,
        String street,
        String postalCode

) {


}
