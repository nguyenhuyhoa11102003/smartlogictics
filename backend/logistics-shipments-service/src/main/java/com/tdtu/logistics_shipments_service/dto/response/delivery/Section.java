package com.tdtu.logistics_shipments_service.dto.response.delivery;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Section {
	@JsonProperty("id")
	String id;

	@JsonProperty("type")
	String type;

	@JsonProperty("departure")
	Event departure;

	@JsonProperty("arrival")
	Event arrival;

	@JsonProperty("summary")
	Summary summary;

	@JsonProperty("notices")
	List<Notice> notices;

	@JsonProperty("transport")
	Transport transport;
}
