package com.tdtu.logistics_users_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String province; // Tỉnh.

    private String provinceCode;

    private String district; // Huyện.

    private String districtCode;

    private String ward;     // Xã/Phường.

    private String wardCode;

    private String street;   // Đường.

    private String postalCode;
}
