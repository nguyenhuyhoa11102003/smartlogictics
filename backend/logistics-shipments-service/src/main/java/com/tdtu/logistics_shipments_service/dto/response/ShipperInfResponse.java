package com.tdtu.logistics_shipments_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipperInfResponse {

    private String employeeCode; // Mã nhân viên.

    private String department; // Phòng ban.

    private String position; // Vị trí công việc (VD: Manager, Employee, Shipper).

    private String startDate; // Ngày bắt đầu làm việc.

    private String vehicleType; // Loại phương tiện (VD: Xe máy, Xe tải).

    private String licensePlate; // Biển số xe.

    private String deliveryArea; // Khu vực giao hàng.

    private String email;

    private String phoneNumber;

    private String fullName;

    private String dateOfBirth;

    private String gender;

    private String status;

}
