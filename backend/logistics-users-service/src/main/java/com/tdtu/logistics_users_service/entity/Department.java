package com.tdtu.logistics_users_service.entity;

import com.tdtu.logistics_users_service.enumrators.DepartmentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name; // Tên phòng ban/chi nhánh.

    @Enumerated(EnumType.STRING)
    private DepartmentType type; // Loại phòng ban.

    @Column(nullable = false)
    private String location; // Địa chỉ cụ thể của chi nhánh.

    @Column
    private String phoneNumber;
}