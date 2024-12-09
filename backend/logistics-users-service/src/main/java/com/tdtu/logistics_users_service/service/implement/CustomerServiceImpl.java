package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.request.CreateCustomerRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateCustomerRequest;
import com.tdtu.logistics_users_service.dto.response.CustomerInfResponse;
import com.tdtu.logistics_users_service.entity.Customer;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.CustomerMapper;
import com.tdtu.logistics_users_service.repository.CustomerRepository;
import com.tdtu.logistics_users_service.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;

    final CustomerMapper customerMapper;

    @Override
    public CustomerInfResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        log.info("Logistics-Users-Service -> Customer-Service -> Create-Customer: Create customer: {}", createCustomerRequest.getEmail());

        Customer customer = customerMapper.toCustomer(createCustomerRequest);

        return customerMapper.toCustomerInfResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerInfResponse updateCustomer(String id, UpdateCustomerRequest customerRequest) {
        log.info("Logistics-Users-Service -> Customer-Service -> Update-Customer: Update customer: {}", id);

        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Update-Customer: Customer not found with id: {}", id);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        return customerMapper.toCustomerInfResponse(customerRepository.save(updateCustomerFromRequest(customer, customerRequest)));
    }

    private Customer updateCustomerFromRequest(Customer customer, UpdateCustomerRequest customerRequest) {

        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setFullName(customerRequest.getFullName());

        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setGender(customerRequest.getGender());

        return customer;
    }

    @Override
    public CustomerInfResponse getCustomerById(String id) {
        log.info("Logistics-Users-Service -> Customer-Service -> Get-Customer-By-ID: Get customer by id: {}", id);

        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Get-Customer: Customer not found with id: {}", id);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        return customerMapper.toCustomerInfResponse(customer);
    }

    @Override
    public CustomerInfResponse getCustomerByEmail(String email) {
        log.info("Logistics-Users-Service -> Customer-Service -> Get-Customer-By-Email: Get customer by email: {}", email);

        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Get-Customer: Customer not found with email: {}", email);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        return customerMapper.toCustomerInfResponse(customer);
    }

    @Override
    public CustomerInfResponse getCustomerByPhoneNumber(String phoneNumber) {
        log.info("Logistics-Users-Service -> Customer-Service -> Get-Customer-By-Phone-Number: Get customer by phone number: {}", phoneNumber);

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Get-Customer: Customer not found with phone number: {}", phoneNumber);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        return customerMapper.toCustomerInfResponse(customer);
    }

}
