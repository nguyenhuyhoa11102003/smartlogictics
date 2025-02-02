package com.tdtu.logistics_shipments_service.dto.response;


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
	double capacity;
	String status;
	AddressInfResponse addressDetail;
	String address;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
}
