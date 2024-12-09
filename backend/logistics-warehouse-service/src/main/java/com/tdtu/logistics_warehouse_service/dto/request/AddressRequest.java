package com.tdtu.logistics_warehouse_service.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@lombok.NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AddressRequest {
	@NotNull(message = "Province cannot be null")
	@Size(min = 3, max = 100, message = "Province name must be between 3 and 100 characters")
	String province;  // Thành phố

	@NotNull(message = "District cannot be null")
	@Size(min = 3, max = 100, message = "District name must be between 3 and 100 characters")
	String district;  // Quận

	@NotNull(message = "Ward cannot be null")
	@Size(min = 3, max = 100, message = "Ward name must be between 3 and 100 characters")
	String ward;  // Phường

	@NotNull(message = "Street cannot be null")
	@Size(min = 3, max = 100, message = "Street name must be between 3 and 100 characters")
	String street;  // Đường

	@NotNull(message = "Postal code cannot be null")
	@Size(min = 5, max = 10, message = "Postal code must be between 5 and 10 characters")
	String postalCode;  // Mã bưu điện

	@NotNull(message = "Address detail cannot be null")
	@Size(min = 5, max = 255, message = "Address detail must be between 5 and 255 characters")
	String address;  // Địa chỉ đầy đủ
}
