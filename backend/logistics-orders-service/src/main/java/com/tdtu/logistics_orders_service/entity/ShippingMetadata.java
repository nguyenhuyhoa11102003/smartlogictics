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
@Table(name = "shipping_metadata")
public class ShippingMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // UUID của dịch vụ vận chuyển

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    ShippingMethod shippingMethod; // Phương thức vận chuyển

    // Chuyen sang day roi nha'
    @Column(name = "delivered_pickup_date", nullable = false)
    LocalDateTime deliveredDate;  // Ngày pickup

    @Column(name = "delivery_status")
    String deliveryStatus;  // Trạng thái giao hàng (DELIVERED, FAILED)

    @Column(name = "delivery_remarks")
    String deliveryRemarks;  // Ghi chú giao hàng
    // Chuyen sang day roi nha'

    @Column(name = "delivery_estimate_time")
    LocalDateTime deliveryEstimateTime; // Thời gian giao hàng du kien

    @Column(name = "desired_delivery_time")
    LocalDateTime desiredDeliveryTime; // Thời gian giao hàng mong muốn của khách hàng

    @Column(name = "delivery_id")
    String deliveryId; // Liên kết đến thông tin giao hàng
}