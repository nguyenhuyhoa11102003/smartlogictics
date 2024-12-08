package com.tdtu.logistics_users_service.entity;

import com.tdtu.logistics_users_service.enumrators.StaffPosition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "staff")
public class Staff extends User {

    @Column(nullable = false)
    private String employeeCode; // Mã nhân viên.

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // Phòng ban.

    @Column(nullable = false)
    private StaffPosition position; // Vị trí công việc (VD: Manager, Employee).

    @Column(nullable = false)
    private LocalDate startDate; // Ngày bắt đầu làm việc.
}