package com.tdtu.logistics_orders_service.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateSenderRequestDTO {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
    String phoneNumber;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name must not exceed 50 characters")
    String city;

    @NotBlank(message = "District is required")
    @Size(max = 50, message = "District name must not exceed 50 characters")
    String district;

    @NotBlank(message = "Ward is required")
    @Size(max = 50, message = "Ward name must not exceed 50 characters")
    String ward;

    @NotBlank(message = "Street is required")
    @Size(max = 100, message = "Street name must not exceed 100 characters")
    String street;

    @NotBlank(message = "House number is required")
    @Size(max = 20, message = "House number must not exceed 20 characters")
    String houseNumber;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format")
    String email;

    @NotBlank(message = "Delivery time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime deliveryTime; // Thời gian giao hàng
}