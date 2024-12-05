package com.tdtu.logistics_orders_service.dto.response;

import com.tdtu.logistics_orders_service.dto.request.CreatePaymentRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.CreateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.CreateSenderRequestDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderInfResponse {
	String id;

	String shipmentCode; // Mã vận đơn

	String note; // Ghi chú

	String moreRequire; // Mô tả

	String orderCode; // Mã vận đơn

	CreateRecipientRequestDTO recipient; // Liên kết đến người nhận

	CreateSenderRequestDTO sender; // Liên kết đến người gửi

	String statusId; // Trạng thái

	String shipperId; // Liên kết đến shipper

	CreatePaymentRequestDTO payment; // Liên kết với Payment
}
