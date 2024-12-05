package com.tdtu.logistics_orders_service.entity;

import com.tdtu.logistics_orders_service.enumrator.StatusCategory;
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
@Entity(name = "order_status")
@EntityListeners(AuditingEntityListener.class)
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "description", nullable = false)
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    StatusCategory category;
}
