package com.tdtu.logistics_warehouse_service.dto.request;

import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateWarehouseRequest {
	@NotNull(message = "Warehouse name cannot be null")
	@Size(min = 1, max = 100, message = "Warehouse name must be between 1 and 100 characters")
	private String name;

	@Size(max = 20, message = "Phone number must be less than 20 characters")
	private String phoneNumber;

	@NotNull(message = "Warehouse capacity cannot be null")
	private double capacity;

	@NotNull(message = "Warehouse status cannot be null")
	private WarehouseStatus status;

	@NotNull(message = "Warehouse address cannot be null")
	private CreateAddressRequest address;
}
