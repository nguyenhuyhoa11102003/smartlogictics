package com.tdtu.logistics_users_service.service;

import com.tdtu.logistics_users_service.dto.request.CreateAddressRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateAddressRequest;
import com.tdtu.logistics_users_service.dto.response.AddressInfResponse;

public interface AddressService {

    AddressInfResponse createAddress(CreateAddressRequest createAddressRequest);

    AddressInfResponse updateAddress(String userId, UpdateAddressRequest updateAddressRequest);

    AddressInfResponse getAddressByUserId(String userId);

}
