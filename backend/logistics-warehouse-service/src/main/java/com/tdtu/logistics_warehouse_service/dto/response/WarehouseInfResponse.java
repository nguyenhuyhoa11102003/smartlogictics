package com.tdtu.logistics_warehouse_service.dto.response;

import com.tdtu.logistics_warehouse_service.model.Warehouse;
import lombok.*;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WarehouseInfResponse {
	private Long id;  // ID kho

	private String name;  // Tên kho

	private String addressDetail;  // Địa chỉ kho (nếu không trả về đối tượng Address)

	private String phoneNumber;  // Số điện thoại kho

	private double capacity;  // Dung lượng kho

	private WarehouseStatus status;  // Trạng thái kho

	private AddressInfResponse address;  // Thay vì addressId, trả về đối tượng Address

	private LocalDateTime createdAt;  // Thời gian tạo kho

	private LocalDateTime updatedAt;  // Thời gian cập nhật kho


	public static WarehouseInfResponse toWarehouseInfResponse(Warehouse warehouse) {
		return WarehouseInfResponse.builder()
				.id(warehouse.getId())
				.name(warehouse.getName())
				.addressDetail(warehouse.getAddressDetail())
				.phoneNumber(warehouse.getPhoneNumber())
				.capacity(warehouse.getCapacity())
				.status(warehouse.getStatus())
				.address(AddressInfResponse.toAddressInfResponse(warehouse.getAddress()))
				.createdAt(LocalDateTime.from(warehouse.getCreateAt()))
				.updatedAt(LocalDateTime.now())
				.build();

	}
}
