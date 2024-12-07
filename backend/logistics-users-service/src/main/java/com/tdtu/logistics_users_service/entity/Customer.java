package com.tdtu.logistics_users_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
public class Customer extends User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerCode; // Mã khách hàng định danh.

    @Column
    private String loyaltyLevel; // Cấp độ khách hàng: Bronze, Silver, Gold.
}
