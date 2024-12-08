package com.tdtu.logistics_users_service.mapper;

import com.tdtu.logistics_users_service.dto.request.CreateShipperRequest;
import com.tdtu.logistics_users_service.dto.response.ShipperInfResponse;
import com.tdtu.logistics_users_service.entity.Shipper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipperMapper {

    ShipperInfResponse toShipperInfResponse(Shipper shipper);

    Shipper toShipper(CreateShipperRequest shipperRequest);

}
