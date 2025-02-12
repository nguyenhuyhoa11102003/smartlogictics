package com.tdtu.logistics_shipments_service.dto.request;

import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ShipmentStatusUpdateRequest {
	ShipmentStatus status;

}
