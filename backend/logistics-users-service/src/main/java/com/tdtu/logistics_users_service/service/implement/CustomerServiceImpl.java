package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.common.user_service.dto.CustomerInfResponse;
import com.tdtu.logistics_users_service.dto.request.CreateCustomerRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateCustomerRequest;
import com.tdtu.logistics_users_service.entity.Address;
import com.tdtu.logistics_users_service.entity.Customer;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.AddressMapper;
import com.tdtu.logistics_users_service.mapper.CustomerMapper;
import com.tdtu.logistics_users_service.repository.CustomerRepository;
import com.tdtu.logistics_users_service.service.CustomerService;
import jakarta.transaction.Transactional;
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

    final AddressMapper addressMapper;

    @Transactional
    @Override
    public CustomerInfResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        log.info("Logistics-Users-Service -> Customer-Service -> Create-Customer: Create customer: {}", createCustomerRequest.getEmail());

        Customer customer = customerMapper.toCustomer(createCustomerRequest);

        Address address = Address.builder().build();
        customer.setAddress(address);

        return customerMapper.toCustomerInfResponse(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public CustomerInfResponse updateCustomer(String id, UpdateCustomerRequest customerRequest) {
        log.info("Logistics-Users-Service -> Customer-Service -> Update-Customer: Update customer: {}", id);

        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Update-Customer: Customer not found with id: {}", id);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        Address address = addressMapper.toAddress(customerRequest.getAddress());

        customer.setAddress(address);

        log.info("Logistics-Users-Service -> Customer-Service -> Update-Customer: Update customer: {}", id);

        return customerMapper.toCustomerInfResponse(customerRepository.save(updateCustomerFromRequest(customer, customerRequest)));
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
        log.debug("Logistics-Users-Service -> Customer-Service -> Get-Customer-By-Phone-Number: Get customer by phone number: {}", phoneNumber);

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Get-Customer: Customer not found with phone number: {}", phoneNumber);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        return customerMapper.toCustomerInfResponse(customer);
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        log.debug("Logistics-Users-Service -> Customer-Service -> Delete-Customer: Delete customer: {}", customerId);

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Customer-Service -> Delete-Customer: Customer not found with id: {}", customerId);
                    return new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);}
            );

        customerRepository.delete(customer);

        return true;
    }

    private Customer updateCustomerFromRequest(Customer customer, UpdateCustomerRequest customerRequest) {

        customer.setFullName(customerRequest.getFullName());

        customer.setDateOfBirth(customerRequest.getDateOfBirth());

        customer.setGender(customerRequest.getGender());

        customer.setIdentityCard(customerRequest.getIdentityCard());

        return customer;
    }
}
