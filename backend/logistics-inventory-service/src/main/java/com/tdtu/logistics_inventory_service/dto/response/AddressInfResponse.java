package com.tdtu.logistics_inventory_service.dto.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressInfResponse {
	Long id;
	String province;
	String ward;
	String commune;
	String street;
	String postalCode;
	String addressDetail;
	String latitude;
	String longitude;
}
