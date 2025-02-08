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
	WAITING_FOR_PICKUP("Chờ lấy hàng", "The shipment is waiting to be picked up."),
	IN_TRANSIT("Đang vận chuyển", "The shipment is in transit."),
	ARRIVED_AT_WAREHOUSE("Vừa đến kho", "The shipment has just arrived at a warehouse."),
	WAREHOUSE_CHECKED_IN("Đã nhập kho", "The shipment has been checked into the warehouse."),
	DEPARTED_WAREHOUSE("Đã rời kho", "The shipment has departed from the warehouse."),
	OUT_FOR_DELIVERY("Đang giao hàng", "The shipment is out for delivery."),
	DELIVERED("Hoàn thành", "The shipment has been delivered successfully."),
	DELIVERY_FAILED("Giao hàng không thành công", "The shipment delivery has failed."),
	RETURNING("Đang hoàn trả", "The shipment is being returned."),
	CANCELLED("Đã hủy", "The shipment has been cancelled.");
	String status;
	String description;

	ShipmentStatus(String status, String description) {
		this.status = status;
		this.description = description;
	}
}
