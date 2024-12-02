package com.tdtu.logistics_orders_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "sender")
@EntityListeners(AuditingEntityListener.class)
public class Sender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Column(name = "full_name", nullable = false)
    String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name must not exceed 50 characters")
    @Column(name = "city", nullable = false)
    String city;

    @NotBlank(message = "District is required")
    @Size(max = 50, message = "District name must not exceed 50 characters")
    @Column(name = "district", nullable = false)
    String district;

    @NotBlank(message = "Ward is required")
    @Size(max = 50, message = "Ward name must not exceed 50 characters")
    @Column(name = "ward", nullable = false)
    String ward;

    @NotBlank(message = "Street is required")
    @Size(max = 100, message = "Street name must not exceed 100 characters")
    @Column(name = "street", nullable = false)
    String street;

    @NotBlank(message = "House number is required")
    @Size(max = 20, message = "House number must not exceed 20 characters")
    @Column(name = "house_number", nullable = false)
    String houseNumber;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    String email;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "delivery_time", nullable = false)
    LocalDateTime deliveryTime; // Thời gian giao hàng
}
