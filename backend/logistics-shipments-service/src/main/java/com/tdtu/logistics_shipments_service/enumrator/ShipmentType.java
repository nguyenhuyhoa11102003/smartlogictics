package com.tdtu.logistics_shipments_service.enumrator;

public enum ShipmentType {
	ECONOMY("TMĐT Tiết Kiệm", 10000),
	EXPRESS("TMĐT Nhanh", 50000),
	URGENT_SCHEDULED("Hỏa tốc, hẹn giờ", 100000);

	private final String description;
	private final int price;

	ShipmentType(String description, int price) {
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}
}