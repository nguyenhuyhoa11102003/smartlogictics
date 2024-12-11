package com.tdtu.logistics_orders_service.service.client;

import com.tdtu.common.user_service.dto.AddressInfResponse;
import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.common.user_service.dto.ReceiverInfResponse;
import com.tdtu.common.user_service.dto.ShipperInfResponse;
import com.tdtu.logistics_orders_service.configuration.feignClient.ClientConfig;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.service.client.fallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        value = "users-service",
        url = "${user-service.url}",
        configuration = ClientConfig.class,
        fallback = UserServiceFallback.class
)
public interface UserServiceClient {

    @GetMapping(value = "/receiver/get/{id}")
    ApiResponse<ReceiverInfResponse> getReceiverById(@PathVariable String id);

    @GetMapping(value = "/receiver/get-all/{customerId}")
    ApiResponse<List<ReceiverInfResponse>> getAllReceiverByCustomerId(@PathVariable String customerId);

    @GetMapping(value = "/customer/get/{id}")
    ApiResponse<CustomerInfResponse> getCustomerById(@PathVariable String id);

    @GetMapping(value = "/address/{userId}")
    ApiResponse<AddressInfResponse> getAddressByUserId(@PathVariable String userId);

    @GetMapping(value = "/shipper/get/{id}")
    ApiResponse<ShipperInfResponse> getShipperById(@PathVariable String id);



}
