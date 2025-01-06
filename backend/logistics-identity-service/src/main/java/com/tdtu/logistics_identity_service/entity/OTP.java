package com.tdtu.logistics_identity_service.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OTP implements Serializable {
    String otp;
    String identifier; // Người dùng liên quan đến OTP
    String actionId; // thông tin nghiệp vụ
}
