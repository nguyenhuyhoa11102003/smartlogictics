package com.tdtu.logistics_orders_service.entity;

import com.tdtu.logistics_orders_service.enumrator.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotBlank
    @Enumerated(EnumType.STRING) // Lưu giá trị enum dưới dạng String
    @Column(name = "status")
    PaymentStatus status;

    @NotBlank
    @Enumerated(EnumType.STRING) // Lưu giá trị enum dưới dạng String
    @Column(name = "method")
    PaymentStatus method;

    @NotBlank
    BigDecimal amount;

    @NotBlank
    @Column(name = "profile_id", unique = true)
    String profileId;
}
