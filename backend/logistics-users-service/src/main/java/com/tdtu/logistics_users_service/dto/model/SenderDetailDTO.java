package com.tdtu.logistics_users_service.dto.model;

public record SenderDetailDTO(String id, String fullName, String fullAddress) {
    // The 'fullAddress' contains concatenated address details
}