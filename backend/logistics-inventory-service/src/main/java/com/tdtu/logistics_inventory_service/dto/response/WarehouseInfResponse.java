package com.tdtu.logistics_inventory_service.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseInfResponse {
	Long id;
	String name;
	String phoneNumber;
	String status;
	AddressInfResponse addressDetail;
	String address;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;

	double volumeCapacity;
	double weightCapacity;
	double volumeUsed;
	double weightUsed;
	double capacityUsed;
	double capacity;

}
