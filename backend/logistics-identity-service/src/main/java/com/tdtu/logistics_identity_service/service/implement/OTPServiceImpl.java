package com.tdtu.logistics_identity_service.service.implement;

import com.tdtu.logistics_identity_service.entity.OTP;
import com.tdtu.logistics_identity_service.service.OTPService;
import com.tdtu.logistics_identity_service.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OTPServiceImpl<T> implements OTPService<T> {

    final RedisService redisService;
    static final String CACHE_OTP_PREFIX = "otp:";

    @Override
    public OTP generateOTP(String identifier, T actionId) {
        SecureRandom random = new SecureRandom();
        String otpValue = String.format("%06d", random.nextInt(1000000));

        OTP otp = OTP.builder()
                .otp(otpValue)
                .identifier(identifier)
                .actionId(actionId.toString())
                .build();

        String redisKey = CACHE_OTP_PREFIX + identifier + ":" + actionId;
        redisService.setCache(redisKey, otp, 90);

        log.info("Generated OTP: {}", otpValue);
        return otp;
    }

    @Override
    public boolean validateOTP(String otp, String identifier, T actionId) {
        String redisKey = CACHE_OTP_PREFIX + identifier + ":" + actionId;

        OTP cachedOtp = redisService.getCache(redisKey, OTP.class);
        if (cachedOtp == null || !cachedOtp.getOtp().equals(otp)) {
            log.warn("Invalid or expired OTP for key: {}", redisKey);
            return false;
        }

        redisService.deleteCache(redisKey); // Xóa OTP sau khi xác thực thành công
        log.info("Validated OTP successfully for key: {}", redisKey);
        return true;
    }

    @Override
    public void deleteOTP(String identifier, T actionId) {
        String redisKey = CACHE_OTP_PREFIX + identifier + ":" + actionId;
        redisService.deleteCache(redisKey);
        log.info("Deleted OTP for key: {}", redisKey);
    }
}
