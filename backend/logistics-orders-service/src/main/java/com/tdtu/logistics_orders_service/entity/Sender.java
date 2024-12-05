package com.tdtu.logistics_orders_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "sender")
@EntityListeners(AuditingEntityListener.class)
public class Sender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "district", nullable = false)
    String district;

    @Column(name = "ward", nullable = false)
    String ward;

    @Column(name = "street", nullable = false)
    String street;

    @Column(name = "house_number", nullable = false)
    String houseNumber;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "delivery_time", nullable = false)
    LocalDateTime deliveryTime; // Thời gian giao hàng

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<Orders> orders; // Danh sách đơn hàng liên kết với người gửi
}
