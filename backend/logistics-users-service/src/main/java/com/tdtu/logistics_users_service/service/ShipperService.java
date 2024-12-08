package com.tdtu.logistics_users_service.service;

import com.tdtu.logistics_users_service.dto.request.CreateShipperRequest;
import com.tdtu.logistics_users_service.dto.response.ShipperInfResponse;

public interface ShipperService {

    ShipperInfResponse getShipperInfById(String id);

    ShipperInfResponse getShipperInfByStaffId(String staffId);

    ShipperInfResponse createShipper(CreateShipperRequest createShipperRequest);

}
