package com.tdtu.logistics_users_service.dto.request;

import com.tdtu.logistics_users_service.entity.Department;
import com.tdtu.logistics_users_service.enumrators.Gender;
import com.tdtu.logistics_users_service.enumrators.StaffPosition;
import com.tdtu.logistics_users_service.enumrators.UserStatus;
import com.tdtu.logistics_users_service.enumrators.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipperRequest {

    private Department department; // Phòng ban.

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

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
