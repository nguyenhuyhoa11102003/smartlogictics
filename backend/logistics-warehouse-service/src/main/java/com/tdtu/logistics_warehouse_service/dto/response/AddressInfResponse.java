package com.tdtu.logistics_warehouse_service.dto.response;


import com.tdtu.logistics_warehouse_service.model.Address;
import com.tdtu.logistics_warehouse_service.model.Warehouse;
import jakarta.persistence.Column;
import lombok.*;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressInfResponse {
	Long id;

	String province;

	@Column(nullable = false)
	String ward;

	@Column(nullable = false)
	String commune;

	@Column(nullable = false)
	String street;

	@Column(nullable = false)
	String postalCode;

	@Column(nullable = false)
	String addressDetail;

	String latitude;
	String longitude;

	public static AddressInfResponse toAddressInfResponse(Address address) {
		return AddressInfResponse.builder()
				.id(address.getId())
				.addressDetail(address.getAddressDetail())
				.province(address.getProvince())
				.ward(address.getWard())
				.commune(address.getCommune())
				.street(address.getStreet())
				.postalCode(address.getPostalCode())
				.addressDetail(address.getAddressDetail())
				.latitude(address.getLatitude())
				.longitude(address.getLongitude())
				.build();

	}
}
