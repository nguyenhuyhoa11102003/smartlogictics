package com.tdtu.logistics_orders_service.dto.request;

import com.tdtu.logistics_orders_service.dto.model.InformationOrderDTO;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private OrderStatus orderCreationStatus;

    private String type;

    private String customerCode;

    private String contractCode;

    private InformationOrderDTO informationOrder;
}