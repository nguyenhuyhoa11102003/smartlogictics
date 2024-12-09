package com.tdtu.logistics_users_service.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "receivers")
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // Người tạo đơn hàng.

    @Column(nullable = false)
    private String fullName; // Tên đầy đủ người nhận.

    @Column(nullable = false)
    private String phoneNumber; // Số điện thoại người nhận.

    @Column(nullable = false)
    private String email; // Email liên hệ.

    @Column(nullable = false)
    private String province; // Tỉnh.

    @Column(nullable = false)
    private String district; // Huyện.

    @Column(nullable = false)
    private String ward; // Xã/Phường.

    @Column(nullable = false)
    private String street; // Đường.

    @Column
    private String postalCode; // Mã bưu chính.
}

