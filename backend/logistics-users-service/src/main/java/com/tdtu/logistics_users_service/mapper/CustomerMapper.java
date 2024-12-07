package com.tdtu.logistics_users_service.mapper;

import com.tdtu.logistics_users_service.dto.request.CreateCustomerRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateCustomerRequest;
import com.tdtu.logistics_users_service.dto.response.CustomerInfResponse;
import com.tdtu.logistics_users_service.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CreateCustomerRequest customerRequest);

    Customer toCustomer(UpdateCustomerRequest customerRequest);

    CustomerInfResponse toCustomerInfResponse(Customer customer);

}
