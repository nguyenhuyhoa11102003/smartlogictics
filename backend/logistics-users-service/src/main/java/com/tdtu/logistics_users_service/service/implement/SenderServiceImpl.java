package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.model.SenderDetailDTO;
import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import com.tdtu.logistics_users_service.entity.Address;
import com.tdtu.logistics_users_service.entity.Customer;
import com.tdtu.logistics_users_service.entity.Sender;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.SenderMapper;
import com.tdtu.logistics_users_service.repository.AddressRepository;
import com.tdtu.logistics_users_service.repository.CustomerRepository;
import com.tdtu.logistics_users_service.repository.SenderRepository;
import com.tdtu.logistics_users_service.service.SenderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SenderServiceImpl implements SenderService {

    final SenderRepository senderRepository;

    final SenderMapper senderMapper;

    final CustomerRepository customerRepository;

    final AddressRepository addressRepository;

    @Override
    @Transactional
    public SenderInfResponse createSender(CreateSenderRequest createSenderRequest) {
        // Map the request DTO to entity
        Sender sender = senderMapper.toEntity(createSenderRequest);

        // Lấy thông tin người dùng từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String author = authentication.getName();

        if (authentication.isAuthenticated()) {
            // Lấy thông tin từ claims của JWT

            Customer customer = customerRepository.findByEmail(author).orElseThrow(() -> {
                log.error("Logistics-Users-Service -> Sender-Service -> Create-Sender: Customer not found with id: {}", author);
                return new IllegalArgumentException("Customer not found");
            });

            sender.setCustomer(customer);

            Address address = Address.builder()
                    .provinceCode(createSenderRequest.senderProvinceCode())
                    .province(createSenderRequest.senderProvinceName())
                    .districtCode(createSenderRequest.senderDistrictCode())
                    .district(createSenderRequest.senderDistrictName())
                    .wardCode(createSenderRequest.senderCommuneCode())
                    .ward(createSenderRequest.senderCommuneName())
                    .street(createSenderRequest.senderAddress())
                    .postalCode(createSenderRequest.senderPostalCode())
                    .build();


            addressRepository.save(address);
            sender.setAddress(address);

            sender = senderRepository.save(sender);

            log.info("Logistics-Users-Service -> Sender-Service -> Create-Sender: Create sender with customer id: {}", author);
            return senderMapper.toResponse(sender);

        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

    }

    @Override
    public SenderDetailDTO getSenderById(String id) {

        SenderDetailDTO senderDetailDTO = senderRepository.findSenderDetailById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        log.info("Logistics-Users-Service -> Sender-Service -> Get-Sender: Get sender by id: {}", id);

        return senderDetailDTO;
    }

    @Override
    public Page<SenderDetailDTO> searchByCustomerIdDto(String customerId, Pageable pageable) {

        Page<SenderDetailDTO> senders = senderRepository.searchByCustomerIdDto(customerId, pageable);

        log.info("Logistics-Users-Service -> Sender-Service -> Search-Sender: Search sender by customer id: {}", customerId);

        return senders;
    }

    @Override
    @Transactional
    public SenderInfResponse updateSender(String senderId, UpdateSenderRequest updateSenderRequest) {
        // Find the sender by ID
        Sender sender = senderRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        // Update the sender's information
        sender.setFullName(updateSenderRequest.senderName());
        sender.setPhoneNumber(updateSenderRequest.senderPhone());
        sender.setEmail(updateSenderRequest.senderMail());

        Address address = sender.getAddress();

        address.setProvince(updateSenderRequest.senderProvinceName());
        address.setDistrict(updateSenderRequest.senderDistrictName());
        address.setWard(updateSenderRequest.senderCommuneName());
        address.setStreet(updateSenderRequest.senderAddress());
        address.setPostalCode(updateSenderRequest.senderPostalCode());

        // Save the updated sender in the database
        sender.setAddress(address);
        sender = senderRepository.save(sender);

        log.info("Logistics-Users-Service -> Sender-Service -> Update-Sender: Update sender: {}", updateSenderRequest.senderName());

        // Map the entity back to response DTO
        return senderMapper.toResponse(sender);
    }



}
