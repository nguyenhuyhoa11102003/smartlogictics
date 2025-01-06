package com.tdtu.logistics_identity_service.service;

import com.tdtu.logistics_identity_service.entity.OTP;

public interface OTPService<T> {

    /**
     * Generate an OTP and associate it with an identifier and a actionId of type T.
     */
    OTP generateOTP(String identifier, T actionId);

    /**
     * Validate an OTP against the identifier and actionId.
     */
    boolean validateOTP(String otp, String identifier, T actionId);

    /**
     * Delete an OTP associated with the identifier and actionId.
     */
    void deleteOTP(String identifier, T actionId);
}
