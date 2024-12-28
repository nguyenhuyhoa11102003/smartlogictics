package com.tdtu.logistics_orders_service.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryRequest {

	private LocalDateTime deliveredDate;  // Thời gian giao hàng
	private String remarks;  // Ghi chú giao hàng
 }
