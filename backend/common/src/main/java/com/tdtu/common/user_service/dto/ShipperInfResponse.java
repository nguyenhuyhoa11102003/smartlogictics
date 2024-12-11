package com.tdtu.common.user_service.dto;

import com.tdtu.common.user_service.enums.Gender;
import com.tdtu.common.user_service.enums.StaffPosition;
import com.tdtu.common.user_service.enums.UserStatus;
import com.tdtu.common.user_service.enums.VehicleType;
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

    private StaffPosition position; // Vị trí công việc (VD: Manager, Employee, Shipper).

    private LocalDate startDate; // Ngày bắt đầu làm việc.

    private VehicleType vehicleType; // Loại phương tiện (VD: Xe máy, Xe tải).

    private String licensePlate; // Biển số xe.

    private String deliveryArea; // Khu vực giao hàng.

    private String email;

    private String phoneNumber;

    private String fullName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private UserStatus status;

}
