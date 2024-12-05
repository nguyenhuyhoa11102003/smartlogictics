package com.tdtu.logistics_orders_service.viewmodel.order;

public record OrderAddressPostVm(
		String contactName,
		String phone,
		String addressLine1,
		String addressLine2,
		String city,
		String zipCode,
		Long districtId,
		String districtName,
		Long stateOrProvinceId,
		String stateOrProvinceName,
		Long countryId,
		String countryName
) {
}
