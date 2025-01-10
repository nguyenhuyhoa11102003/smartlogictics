package com.tdtu.logistics_shipments_service.dto.response.delivery;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Summary {
	@JsonProperty("duration")
	int duration;

	@JsonProperty("length")
	int length;

	@JsonProperty("baseDuration")
	int baseDuration;
}
