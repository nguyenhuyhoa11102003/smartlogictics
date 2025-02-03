package com.tdtu.logistics_shipments_service.dto.request;


import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AddOrdersToShipmentRequest {
	Long shipmentId;
	List<String> orderIds;
}
