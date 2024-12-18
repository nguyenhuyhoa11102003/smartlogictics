package com.tdtu.logistics_orders_service.mapper;

import com.tdtu.logistics_orders_service.dto.model.InformationOrderDTO;
import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	Orders toEntity(CreateOrderRequest orderRequest);

	Orders toEntity(InformationOrderDTO informationOrderDTO);

	@Mapping(source = "orders.shipmentCode", target = "itemCode")
	@Mapping(source = "orders.shipmentCode", target = "originalItemCode")
	@Mapping(source = "orders.shipmentCode", target = "shipmentId")
	@Mapping(source = "orders.createdAt", target = "createdDate")
	OrderInfResponse toOrderInfResponse(Orders orders);

}
