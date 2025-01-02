package com.tdtu.logistics_shipments_service.enumrator;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;


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
