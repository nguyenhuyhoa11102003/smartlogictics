package com.tdtu.logistics_users_service.entity;

import com.tdtu.logistics_users_service.enumrators.VehicleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shippers")
public class Shipper extends Staff {

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VehicleType vehicleType; // Loại phương tiện (VD: Xe máy, Xe tải).

	@Column(nullable = false)
	private String licensePlate; // Biển số xe.

	@Column(nullable = false)
	private String deliveryArea; // Khu vực giao hàng.

	private String warehouseId; // Thuộc kho nào.

	@Column(name = "available")
	private boolean available = Boolean.TRUE;

}