package com.tdtu.logistics_orders_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "uncategorized_exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(401, "invalid_key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(403, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "unauthorized", HttpStatus.FORBIDDEN),
    EMAIL_INVALID(400, "email_invalid", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(400, "invalid_request", HttpStatus.BAD_REQUEST),

    SENDER_NOT_FOUND(404, "sender_not_found", HttpStatus.NOT_FOUND),
    RECIPIENT_NOT_FOUND(404, "recipient_not_found", HttpStatus.NOT_FOUND),

    PAYMENT_NOT_FOUND(404, "payment_not_found", HttpStatus.NOT_FOUND)
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
