package com.tdtu.logistics_warehouse_service.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateAddressRequest {

	@NotNull(message = "Street name cannot be null")
	@Size(min = 1, max = 255, message = "Street name must be between 1 and 255 characters")
	private String street; // Đường

	@NotNull(message = "City cannot be null")
	@Size(min = 1, max = 100, message = "City name must be between 1 and 100 characters")
	private String province; // Tỉnh/Thành phố

	@NotNull(message = "State cannot be null")
	@Size(min = 1, max = 100, message = "State name must be between 1 and 100 characters")
	private String commune;  // Phường/Xã

	@NotNull(message = " Commune cannot be null")
	@Size(min = 1, max = 100, message = "Commune name must be between 1 and 100 characters")
	private String ward;  // Quận/Huyện

	@NotNull(message = "Postal code cannot be null")
	@Size(min = 1, max = 20, message = "Postal code must be between 1 and 20 characters")
	private String postalCode;  // Mã bưu điện


}