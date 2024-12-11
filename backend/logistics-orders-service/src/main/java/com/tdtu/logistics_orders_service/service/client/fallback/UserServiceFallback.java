package com.tdtu.logistics_orders_service.service.client.fallback;

import com.tdtu.common.user_service.dto.AddressInfResponse;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.common.user_service.dto.ReceiverInfResponse;
import com.tdtu.common.user_service.dto.ShipperInfResponse;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.service.client.UserServiceClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserServiceFallback implements UserServiceClient {

    @Override
    public ApiResponse<ReceiverInfResponse> getReceiverById(String id) {
        return null;
    }

    @Override
    public ApiResponse<List<ReceiverInfResponse>> getAllReceiverByCustomerId(String customerId) {
        return null;
    }

    @Override
    public ApiResponse<CustomerInfResponse> getCustomerById(String id) {
        return null;
    }

    @Override
    public ApiResponse<AddressInfResponse> getAddressByUserId(String userId) {
        return null;
    }

    @Override
    public ApiResponse<ShipperInfResponse> getShipperById(String id) {
        return null;
    }
}
