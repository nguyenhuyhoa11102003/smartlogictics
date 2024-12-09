package com.tdtu.logistics_orders_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "order-good-detail")
@EntityListeners(AuditingEntityListener.class)
public class OrderGoodDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "good_id", nullable = false)
    String goodId;

    @Column(name = "quantity", nullable = false)
    int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    Orders order; // Quan hệ với đơn hàng, mỗi chi tiết đơn hàng thuộc một đơn hàng

}
