package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.request.CreateAddressRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateAddressRequest;
import com.tdtu.logistics_users_service.dto.response.AddressInfResponse;
import com.tdtu.logistics_users_service.entity.Address;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.AddressMapper;
import com.tdtu.logistics_users_service.repository.AddressRepository;
import com.tdtu.logistics_users_service.service.AddressService;
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
public class AddressServiceImpl implements AddressService {

    final AddressRepository addressRepository;

    final AddressMapper addressMapper;

    @Transactional
    @Override
    public AddressInfResponse createAddress(CreateAddressRequest createAddressRequest) {

        Address address = addressMapper.toAddress(createAddressRequest);

        log.info("Logistics-Users-Service -> Address-Service -> Create-Address: Create address: {}", address);

        return addressMapper.toAddressInfResponse(addressRepository.save(address));
    }

    @Transactional
    @Override
    public AddressInfResponse updateAddress(Long id, UpdateAddressRequest updateAddressRequest) {
        log.info("Logistics-Users-Service -> Address-Service -> Update-Address: Update address: {}", id);

        Address address = addressRepository.findById(id).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Address-Service -> Update-Address: Address not found with user id: {}", id);
                    return new AppException(ErrorCode.ADDRESS_NOT_EXISTED);
                }
        );

        return addressMapper.toAddressInfResponse(addressRepository.save(updateAddress(address, updateAddressRequest)));
    }

    @Override
    public AddressInfResponse getAddressById(Long addressId) {
        log.info("Logistics-Users-Service -> Address-Service -> Get-Address-By-User-ID: Get address by user id: {}", addressId);

        Address address = addressRepository.findById(addressId).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Address-Service -> Get-Address: Address not found with user id: {}", addressId);
                    return new AppException(ErrorCode.ADDRESS_NOT_EXISTED);
                }
        );

        return addressMapper.toAddressInfResponse(address);
    }

    private Address updateAddress(Address address, UpdateAddressRequest updateAddressRequest) {
        address.setProvince(updateAddressRequest.getProvince());
        address.setDistrict(updateAddressRequest.getDistrict());

        address.setWard(updateAddressRequest.getWard());
        address.setStreet(updateAddressRequest.getStreet());

        address.setPostalCode(updateAddressRequest.getPostalCode());
        return address;
    }
}
