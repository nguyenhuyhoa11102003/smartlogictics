package com.tdtu.logistics_orders_service.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SenderInfResponseDTO {

    private String id; // ID của Sender

    private String fullName; // Tên đầy đủ của người gửi

    private String phoneNumber; // Số điện thoại của người gửi

    private String address;

    private String email; // Email của người gửi

    private String deliveryTime; // Thời gian giao hàng của người gửi
}
