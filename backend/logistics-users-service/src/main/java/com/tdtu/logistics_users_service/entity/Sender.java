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
@Table(name = "sender")
public class Sender {
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

    private String senderProvinceCode; // Mã tỉnh của người gửi

    @Column(nullable = false)
    private String district; // Huyện.

    private String senderDistrictCode; // Mã quận/huyện của người gửi

    @Column(nullable = false)
    private String ward; // Xã/Phường.

    private String senderCommuneCode; // Mã xã/phường của người gửi

    @Column(nullable = false)
    private String street; // Đường.

    @Column
    private String postalCode; // Mã bưu chính.
}
