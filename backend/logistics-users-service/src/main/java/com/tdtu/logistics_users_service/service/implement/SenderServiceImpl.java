package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.request.CreateSenderRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateSenderRequest;
import com.tdtu.logistics_users_service.dto.response.SenderInfResponse;
import com.tdtu.logistics_users_service.entity.Sender;
import com.tdtu.logistics_users_service.mapper.SenderMapper;
import com.tdtu.logistics_users_service.repository.SenderRepository;
import com.tdtu.logistics_users_service.service.SenderService;
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
public class SenderServiceImpl implements SenderService {

    SenderRepository senderRepository;

    SenderMapper senderMapper;

    @Override
    @Transactional
    public SenderInfResponse createSender(CreateSenderRequest createSenderRequest) {
        // Map the request DTO to entity
        Sender sender = senderMapper.toEntity(createSenderRequest);

        // Save the sender in the database
        sender = senderRepository.save(sender);

        // Map the entity back to response DTO

        log.info("Logistics-Users-Service -> Sender-Service -> Create-Sender: Create sender: {}", createSenderRequest.senderName());

        return senderMapper.toResponse(sender);
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
