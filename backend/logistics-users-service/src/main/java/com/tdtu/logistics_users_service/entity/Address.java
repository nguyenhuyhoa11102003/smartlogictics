package com.tdtu.logistics_users_service.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String province; // Tỉnh.
    private String district; // Huyện.
    private String ward;     // Xã/Phường.
    private String street;   // Đường.
    private String postalCode;
}