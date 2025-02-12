
package com.tdtu.logistics_payments_service.model;

import com.tdtu.logistics_payments_service.model.enumeration.EPaymentMethod;
import com.tdtu.logistics_payments_service.model.enumeration.EPaymentStatus;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private BigDecimal amount;
    private BigDecimal paymentFee;
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private EPaymentStatus paymentStatus;
    private String gatewayTransactionId;
    private String failureMessage;
    @CreationTimestamp
    private ZonedDateTime createdOn;
    @UpdateTimestamp
    private ZonedDateTime lastModifiedOn;
}