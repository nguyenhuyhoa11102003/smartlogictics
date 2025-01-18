package com.tdtu.logistics_users_service.service;

import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.common.user_service.dto.CreateCustomerRequest;
import com.tdtu.logistics_users_service.dto.model.CustomerDetailDTO;
import com.tdtu.logistics_users_service.dto.request.UpdateCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {

    CustomerInfResponse createCustomer(CreateCustomerRequest createCustomerRequest);

    CustomerInfResponse updateCustomer(String id, UpdateCustomerRequest customerRequest);

    CustomerInfResponse getCustomerById(String id);

    CustomerInfResponse getCustomerByEmail(String email);

    CustomerInfResponse getCustomerByPhoneNumber(String phoneNumber);

    // Method to find Customer detail by ID with address concatenation
    CustomerDetailDTO findCustomerDetailById(String id);

    // Method to get a page of Customer details with address concatenation
    Page<CustomerDetailDTO> searchAllCustomersWithAddress(Pageable pageable);

    boolean deleteCustomer(String customerId);
}
