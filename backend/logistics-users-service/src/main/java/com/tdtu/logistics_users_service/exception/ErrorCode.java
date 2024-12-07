package com.tdtu.logistics_users_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "uncategorized_exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(401, "invalid_key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(400, "user_existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(404, "username_invalid", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(405, "invalid_password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(402, "user_not_existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(403, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "unauthorized", HttpStatus.FORBIDDEN),
    INVALID_DOB(403, "invalid_dob", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(403, "permission_not_existed", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXISTED(400, "role_already_existed", HttpStatus.BAD_REQUEST),
    PROFILE_NOT_EXISTED(400, "profile_not_existed", HttpStatus.NOT_FOUND),
    EMAIL_INVALID(400, "email_invalid", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(400, "invalid_request", HttpStatus.BAD_REQUEST),

    RECEIVER_NOT_EXISTED(404, "receiver_not_existed", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int statusCode, String message, HttpStatus httpStatus) {
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final int statusCode;
    private final String message;
    private final HttpStatus httpStatus;
}
