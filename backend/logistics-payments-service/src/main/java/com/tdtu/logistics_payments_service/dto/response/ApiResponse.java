package com.tdtu.logistics_payments_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	@Builder.Default
	int code = 200;

	@Builder.Default
	@JsonProperty("isSuccess")
	boolean isSuccess = Boolean.TRUE;

	@JsonProperty("result")
	T result;

	@JsonProperty("message")
	String message;

	@JsonProperty("timestamp")
	LocalDate timestamp;
}
