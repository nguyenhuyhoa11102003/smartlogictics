package com.tdtu.logistics_orders_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class LocationRequest {

	String pickupLatitude;
	String pickupLongitude;

	String deliveryLatitude;
	String deliveryLongitude;
}
