package com.tdtu.logistics_users_service.service;

import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.logistics_users_service.dto.request.CreateCustomerRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateCustomerRequest;

public interface CustomerService {

    CustomerInfResponse createCustomer(CreateCustomerRequest createCustomerRequest);

    CustomerInfResponse updateCustomer(String id, UpdateCustomerRequest customerRequest);

    CustomerInfResponse getCustomerById(String id);

    CustomerInfResponse getCustomerByEmail(String email);

    CustomerInfResponse getCustomerByPhoneNumber(String phoneNumber);

    boolean deleteCustomer(String customerId);
}
