package com.tdtu.logistics_orders_service.dto.request;


import com.tdtu.logistics_orders_service.enumrator.PaymentStatus;
import com.tdtu.logistics_orders_service.enumrator.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMetadataRequest {

	@NotNull(message = "Mã khách hàng không được để trống")
	String customerCode;

	@NotNull(message = "Tổng cước không được để trống")
	@PositiveOrZero(message = "Tổng cước phải lớn hơn hoặc bằng 0")
	BigDecimal totalCost;

	@NotNull(message = "Tiền thu hộ không được để trống")
	@PositiveOrZero(message = "Tiền thu hộ phải lớn hơn hoặc bằng 0")
	BigDecimal codAmount;

	@NotBlank(message = "Người trả cước không được để trống")
	String payer;

	@NotNull(message = "Tổng giá trị đơn hàng không được để trống")
	@PositiveOrZero(message = "Tổng giá trị đơn hàng phải lớn hơn hoặc bằng 0")
	BigDecimal totalAmount;

	String paymentCode; // Lien ket voi Payment-Service (có thể null)

	@NotNull(message = "Trạng thái thanh toán không được để trống")
	PaymentStatus paymentStatus;

	@NotNull(message = "Loại thanh toán không được để trống")
	PaymentType paymentType;
}
