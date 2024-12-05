package com.tdtu.logistics_orders_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorsResponse<T> {
    @Builder.Default
    int code = 405;

    @Builder.Default
    boolean isSuccess = Boolean.FALSE;

    String message;

    T errors;

    @Builder.Default
    Date timestamp = new Date();
}
