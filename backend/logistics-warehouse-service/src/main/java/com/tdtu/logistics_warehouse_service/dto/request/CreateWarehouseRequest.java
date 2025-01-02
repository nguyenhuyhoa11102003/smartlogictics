package com.tdtu.logistics_warehouse_service.dto.request;

import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateWarehouseRequest {
	@NotNull(message = "Warehouse name cannot be null")
	@Size(min = 1, max = 100, message = "Warehouse name must be between 1 and 100 characters")
	String name;

	@Size(max = 20, message = "Phone number must be less than 20 characters")
	String phoneNumber;

	@NotNull(message = "Warehouse capacity cannot be null")
	double capacity;

	@NotNull(message = "Warehouse status cannot be null")
	WarehouseStatus status;

	@NotNull(message = "Warehouse address cannot be null")
	CreateAddressRequest address;
}
