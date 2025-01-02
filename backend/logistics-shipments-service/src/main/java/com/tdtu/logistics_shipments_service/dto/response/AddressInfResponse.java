package com.tdtu.logistics_shipments_service.dto.response;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
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
