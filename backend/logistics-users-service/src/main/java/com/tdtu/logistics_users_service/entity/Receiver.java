package com.tdtu.logistics_users_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address; // Quan hệ 1-1 với Address.
}
