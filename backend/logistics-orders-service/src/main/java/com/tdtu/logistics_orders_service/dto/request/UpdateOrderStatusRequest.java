package com.tdtu.logistics_orders_service.dto.request;

import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderStatusRequest {

    String orderId;

    OrderStatus orderStatus;

}
