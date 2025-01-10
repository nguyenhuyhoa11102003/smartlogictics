package com.tdtu.logistics_identity_service.exception;

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

    FORBIDDEN(403, "forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "not_found", HttpStatus.NOT_FOUND),
<<<<<<< HEAD
    INTERNAL_SERVER_ERROR(500, "internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR);
=======
    INTERNAL_SERVER_ERROR(500, "internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR),

    ACCOUNT_NOT_FOUND(404, "account_not_found", HttpStatus.NOT_FOUND),

    WORKFLOW_FAILED(500, "workflow_failed", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
>>>>>>> 1bd25e14c88250c163d958725ad6e4ed8150f0e4

    ErrorCode(int statusCode, String message, HttpStatus httpStatus) {
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final int statusCode;
    private final String message;
    private final HttpStatus httpStatus;
}