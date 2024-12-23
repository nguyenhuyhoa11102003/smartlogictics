package com.tdtu.logistics_users_service.enumrators;

public enum VehicleType {

	// giao hanh nhanh
	MOTORBIKE("Xe máy"),

	// giao hang trong noi thanh
	VAN("Xe tải 1 tấn"),
	// giao hàng liên tỉnh
	TRUCK("Xe tải 15 tấn");

	private final String name;

	VehicleType(String value) {
		this.name = value;
	}

	public String getValue() {
		return name;
	}
}
