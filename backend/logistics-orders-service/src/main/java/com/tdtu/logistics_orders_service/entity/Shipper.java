package com.tdtu.logistics_orders_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "shippers")
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    String phoneNumber;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    String email;

    @Column(name = "vehicle_number", nullable = false, unique = true, length = 20)
    String vehicleNumber; // Số xe của shipper
}