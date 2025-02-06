package com.tdtu.logistics_warehouse_service.dto.response;

import com.tdtu.logistics_warehouse_service.model.Warehouse;
import lombok.*;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.ZoneId;


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

	double capacityUsed;

	WarehouseStatus status;

	AddressInfResponse addressDetail;
	String address;

	double  volumeCapacity;

	double weightCapacity;

	double volumeUsed;

	double weightUsed;

	LocalDateTime createdAt;

	LocalDateTime updatedAt;

	public static WarehouseInfResponse toWarehouseInfResponse(Warehouse warehouse) {
		return WarehouseInfResponse.builder()
				.id(warehouse.getId())
				.name(warehouse.getName())
				.phoneNumber(warehouse.getPhoneNumber())
				.capacity(warehouse.getCapacity())
				.capacityUsed(warehouse.getCapacityUsed())
				.status(warehouse.getStatus())
				.addressDetail(AddressInfResponse.toAddressInfResponse(warehouse.getAddress()))
				.address(warehouse.getAddress().getAddressDetail())
				.createdAt(LocalDateTime.ofInstant(warehouse.getCreateAt(), ZoneId.systemDefault()))
				.updatedAt(LocalDateTime.now())
				.volumeCapacity(warehouse.getVolumeCapacity())
				.weightCapacity(warehouse.getWeightCapacity())
				.volumeUsed(warehouse.getVolumeUsed())
				.weightUsed(warehouse.getWeightUsed())
				.build();
	}
}
