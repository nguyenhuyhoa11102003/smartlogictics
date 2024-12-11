package com.tdtu.logistics_orders_service.mapper;

import com.tdtu.logistics_orders_service.dto.model.InformationOrderDTO;
import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Orders toEntity(CreateOrderRequest orderRequest);

    Orders toEntity(InformationOrderDTO informationOrderDTO);

    OrderInfResponse toOrderInfResponse(Orders orders);



}
