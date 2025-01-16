package com.tdtu.logistics_orders_service.entity;

import com.tdtu.common.orders_service.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "shipping_services")
public class ShippingService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id; // UUID của dịch vụ vận chuyển

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    ShippingMethod shippingMethod; // Phương thức vận chuyển

    @Column(name = "expected_delivery_time", nullable = false)
    LocalDateTime expectedDeliveryTime; // Thời gian giao hàng dự kiến

    @Column(name = "desired_delivery_time")
    LocalDateTime desiredDeliveryTime; // Thời gian giao hàng mong muốn của khách hàng

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Orders order; // Quan hệ với đơn hàng
}