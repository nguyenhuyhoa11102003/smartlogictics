package com.tdtu.logistics_orders_service.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {
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
