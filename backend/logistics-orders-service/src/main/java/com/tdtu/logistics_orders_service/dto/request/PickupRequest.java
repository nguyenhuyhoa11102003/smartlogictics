package com.tdtu.logistics_orders_service.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PickupRequest {
	private LocalDateTime pickupDate;
	private String associatedAccountNumber;
	private String originDetail;
	private String totalWeight;
	private int packageCount;
	private String carrierCode;
	private String remarks;
	private String pickupType;
	private String status; // PENDING, ASSIGNED, COMPLETED
}
