package com.tdtu.logistics_orders_service.configuration.feignClient;


import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = extractResponseBody(response);

        return switch (response.status()) {
            case 400 -> {
                log.warn("Bad request detected. Method: {}, Status: {}, Body: {}",
                        methodKey, response.status(), responseBody);

                yield new AppException(ErrorCode.INVALID_REQUEST);
            }
            case 403 -> {
                log.error("Access forbidden. Method: {}, Status: {}, Body: {}",
                        methodKey, response.status(), responseBody);

                yield new AppException(ErrorCode.FORBIDDEN);
            }
            case 404 -> {
                log.info("Resource not found. Method: {}, Status: {}, Body: {}",
                        methodKey, response.status(), responseBody);

                yield new AppException(ErrorCode.NOT_FOUND);
            }
            case 500 -> {
                log.error("Internal server error occurred. Method: {}, Status: {}, Body: {}",
                        methodKey, response.status(), responseBody);

                yield new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
            default -> {
                log.error("Uncategorized error. Method: {}, Status: {}, Body: {}",
                        methodKey, response.status(), responseBody);

                yield new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        };
    }

    private String extractResponseBody(Response response) {
        try {
            if (response.body() != null) {
                return IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("Failed to read response body for logging", e);
        }
        return "No response body available";
    }
}
