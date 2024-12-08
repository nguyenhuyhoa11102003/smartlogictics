package com.tdtu.logistics_users_service.entity;

import com.tdtu.logistics_users_service.enumrators.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shippers")
public class Shipper extends Staff {

    @Column(nullable = false)
    private VehicleType vehicleType; // Loại phương tiện (VD: Xe máy, Xe tải).

    @Column(nullable = false)
    private String licensePlate; // Biển số xe.

    @Column(nullable = false)
    private String deliveryArea; // Khu vực giao hàng.

}