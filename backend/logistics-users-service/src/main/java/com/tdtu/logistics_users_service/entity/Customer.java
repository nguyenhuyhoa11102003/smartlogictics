package com.tdtu.logistics_users_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
public class Customer extends User {

    @Column(nullable = false)
    private String customerCode; // Mã khách hàng định danh.

    @Column
    private String loyaltyLevel; // Cấp độ khách hàng: Bronze, Silver, Gold.
}
