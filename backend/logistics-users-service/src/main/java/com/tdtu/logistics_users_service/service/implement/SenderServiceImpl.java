package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import com.tdtu.logistics_users_service.entity.Customer;
import com.tdtu.logistics_users_service.entity.Sender;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.SenderMapper;
import com.tdtu.logistics_users_service.repository.CustomerRepository;
import com.tdtu.logistics_users_service.repository.SenderRepository;
import com.tdtu.logistics_users_service.service.SenderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SenderServiceImpl implements SenderService {

    final SenderRepository senderRepository;

    final SenderMapper senderMapper;

    final CustomerRepository customerRepository;

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

            sender = senderRepository.save(sender);

            log.info("Logistics-Users-Service -> Sender-Service -> Create-Sender: Create sender with customer id: {}", author);
            return senderMapper.toResponse(sender);

        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

    }

    @Override
    @Transactional
    public SenderInfResponse updateSender(String senderId, UpdateSenderRequest updateSenderRequest) {
        // Find the sender by ID
        Sender existingSender = senderRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        // Update the sender's information
        existingSender.setFullName(updateSenderRequest.senderName());
        existingSender.setPhoneNumber(updateSenderRequest.senderPhone());
        existingSender.setEmail(updateSenderRequest.senderMail());
        existingSender.setProvince(updateSenderRequest.senderProvinceName());
        existingSender.setDistrict(updateSenderRequest.senderDistrictName());
        existingSender.setWard(updateSenderRequest.senderCommuneName());
        existingSender.setStreet(updateSenderRequest.senderAddress());
        existingSender.setPostalCode(updateSenderRequest.senderPostalCode());

        // Save the updated sender in the database
        existingSender = senderRepository.save(existingSender);

        log.info("Logistics-Users-Service -> Sender-Service -> Update-Sender: Update sender: {}", updateSenderRequest.senderName());

        // Map the entity back to response DTO
        return senderMapper.toResponse(existingSender);
    }

}
