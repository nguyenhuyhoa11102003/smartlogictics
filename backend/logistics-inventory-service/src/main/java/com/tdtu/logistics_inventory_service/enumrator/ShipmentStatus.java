package com.tdtu.logistics_inventory_service.enumrator;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ShipmentStatus {
	PENDING("Chờ xử lý", "The shipment is pending."),
	IN_TRANSIT("Đang vận chuyển", "The shipment is in transit."),
	COMPLETED("Hoàn thành", "The shipment has been completed."),
	CANCELLED("Đã hủy", "The shipment has been cancelled.");
	String status;
	String description;

	ShipmentStatus(String status, String description) {
		this.status = status;
		this.description = description;
	}
}
