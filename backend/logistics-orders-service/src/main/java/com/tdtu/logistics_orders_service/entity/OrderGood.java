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
@Entity(name = "order_good")
@EntityListeners(AuditingEntityListener.class)
public class OrderGood {

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
	Order order;
}
