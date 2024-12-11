package com.tdtu.logistics_notification_service.exception;

import com.tdtu.logistics_notification_service.dto.response.ErrorsResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorsResponse<String>> handlingRuntimeException(RuntimeException exception) {

        log.error("Runtime Error: {}", exception.getMessage(), exception);

        Set<String> errors = Set.of(exception.getMessage());

        return ResponseEntity.badRequest()
                .body(ErrorsResponse.<String>builder()
                        .code(500)
                        .errors(String.valueOf(errors))
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ErrorsResponse<String>> handlingAppException(AppException exception) {
        log.error("AppException: {}", exception.getMessage(), exception);

        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(ErrorsResponse.<String>builder()
                        .code(exception.getErrorCode().getStatusCode())
                        .errors(String.valueOf(Set.of(exception.getMessage())))
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse<Map<String, String>>> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest()
                .body(ErrorsResponse.<Map<String, String>>builder()
                        .code(400)
                        .errors(errors)
                        .build());
    }

//    @ExceptionHandler(value = AuthorizationDeniedException.class)
//    public ResponseEntity<ErrorsResponse> authorizationDeniedException(AuthorizationDeniedException exception) {
//        log.error("AuthorizationDeniedException Error: {}, Class: {}", exception.getCause(), exception.getClass());
//
//        return ResponseEntity.badRequest()
//                .body(ErrorsResponse.builder()
//                        .message(ErrorCode.UNAUTHORIZED.getMessage())
//                        .code(ErrorCode.UNAUTHORIZED.getStatusCode())
//                        .build());
//    }

}
