package com.tdtu.logistics_warehouse_service.dto.response;


import com.tdtu.logistics_warehouse_service.model.Address;
import com.tdtu.logistics_warehouse_service.model.Warehouse;
import jakarta.persistence.Column;
import lombok.*;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressInfResponse {
	private Long id;  // ID kho

	private String province; // Tỉnh.

	@Column(nullable = false)
	private String ward; // Huyện.

	@Column(nullable = false)
	private String commune; // Xã/Phường.

	@Column(nullable = false)
	private String street; // Đường.

	@Column(nullable = false)
	private String postalCode; // Mã bưu chính.

	@Column(nullable = false)
	private String addressDetail; // Địa chỉ đầy đủ.


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
				.build();

	}
}
