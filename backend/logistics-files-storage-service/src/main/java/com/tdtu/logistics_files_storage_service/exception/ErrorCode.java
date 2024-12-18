package com.tdtu.logistics_files_storage_service.exception;

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

    FORBIDDEN(403, "forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "not_found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(500, "internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR),

    SENDER_NOT_FOUND(404, "sender_not_found", HttpStatus.NOT_FOUND),
    RECIPIENT_NOT_FOUND(404, "recipient_not_found", HttpStatus.NOT_FOUND),

    S3_DOWNLOAD_FAILED(400, "s3_download_failed", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_UPLOAD_FAILED(400, "s3_upload_failed", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_DELETE_FAILED(400, "s3_delete_failed", HttpStatus.INTERNAL_SERVER_ERROR),
    S3_FILE_NOT_FOUND(404, "s3_file_not_found", HttpStatus.NOT_FOUND),
    S3_BUCKET_NOT_FOUND(404, "s3_bucket_not_found", HttpStatus.NOT_FOUND),

    RESOURCES_NOT_FOUND(404, "resources_not_found", HttpStatus.NOT_FOUND),
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
