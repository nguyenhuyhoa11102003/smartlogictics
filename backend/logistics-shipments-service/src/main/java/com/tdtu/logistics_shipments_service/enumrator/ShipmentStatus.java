package com.tdtu.logistics_shipments_service.enumrator;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ShipmentStatus {
	PENDING("Chờ xử lý", "The shipment is pending.");
	String status;
	String description;

	ShipmentStatus(String status, String description) {
		this.status = status;
		this.description = description;
	}
}
