package com.tdtu.logistics_warehouse_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "addresses")
@EntityListeners(AuditingEntityListener.class)
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // ID.

	@Column(nullable = false)
	private String province; // Tỉnh.

	@Column(nullable = false)
	private String ward; // Huyện.

	@Column(nullable = false)
	private String commune; // Xã/Phường.

	@Column(nullable = false)
	private String street; // Đường.

	@Column(nullable = false)
	private String postalCode; // Mã bưu chính.

	@Column(nullable = false)
	private String addressDetail; // Địa chỉ đầy đủ.
}
