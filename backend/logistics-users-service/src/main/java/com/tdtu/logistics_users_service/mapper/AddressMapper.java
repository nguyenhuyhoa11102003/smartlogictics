package com.tdtu.logistics_users_service.mapper;

import com.tdtu.logistics_users_service.dto.request.CreateAddressRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateAddressRequest;
import com.tdtu.logistics_users_service.dto.response.AddressInfResponse;
import com.tdtu.logistics_users_service.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressInfResponse toAddressInfResponse(Address address);

    Address toAddress(CreateAddressRequest addressRequest);

    Address toAddress(UpdateAddressRequest addressRequest);

}
