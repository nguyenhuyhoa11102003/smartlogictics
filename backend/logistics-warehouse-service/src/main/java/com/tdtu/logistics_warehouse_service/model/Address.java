package com.tdtu.logistics_warehouse_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "addresses")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String province; // Tỉnh/Thành phố.

	@Column(nullable = false)
	String ward; // Quận/Huyện.

	@Column(nullable = false)
	String commune; // Phường/Xã.

	@Column(nullable = false)
	String street; // Đường.

	@Column(nullable = false)
	String addressDetail; // Địa chỉ đầy đủ.

	@Column(nullable = false)
	String postalCode;
	@Column(nullable = false)
	String latitude;

	@Column(nullable = false)
	String longitude;

}
