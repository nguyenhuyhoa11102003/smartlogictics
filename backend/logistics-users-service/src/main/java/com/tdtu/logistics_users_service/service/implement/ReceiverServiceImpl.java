package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.request.CreateReceiverRequest;
import com.tdtu.logistics_users_service.dto.request.UpdateReceiverRequest;
import com.tdtu.logistics_users_service.dto.response.ReceiverInfResponse;
import com.tdtu.logistics_users_service.entity.Receiver;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.ReceiverMapper;
import com.tdtu.logistics_users_service.repository.ReceiverRepository;
import com.tdtu.logistics_users_service.service.ReceiverService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiverServiceImpl implements ReceiverService {

    final ReceiverRepository receiverRepository;

    final ReceiverMapper receiverMapper;

    @Transactional
    @Override
    public ReceiverInfResponse createReceiver(CreateReceiverRequest createReceiverRequest) {
        log.info("Logistics-Users-Service -> Receiver-Service -> Create-Receiver: Create receiver: {}", createReceiverRequest.getFullName());

        Receiver receiver = receiverMapper.toReceiver(createReceiverRequest);

        return receiverMapper.toReceiverInfResponse(receiverRepository.save(receiver));
    }



    @Override
    public ReceiverInfResponse getReceiverById(String id) {
        log.info("Logistics-Users-Service -> Receiver-Service -> Get-Receiver-By-ID: Get receiver by id: {}", id);

        Receiver receiver = receiverRepository.findById(id).orElseThrow(() -> {
                    log.error("Logistics-Users-Service -> Receiver-Service -> Get-Receiver: Receiver not found with id: {}", id);
                    return new AppException(ErrorCode.RECEIVER_NOT_EXISTED);}
            );

        return receiverMapper.toReceiverInfResponse(receiver);
    }

    @Override
    public List<ReceiverInfResponse> getAllReceiversByCustomerId(String customerId) {
        log.info("Logistics-Users-Service -> Receiver-Service -> Get-All-Receiver: Get all receivers by customer id: {}", customerId);

        List<Receiver> receivers = receiverRepository.findByCustomer(customerId);

        return receivers.stream().map(receiverMapper::toReceiverInfResponse).toList();
    }

    @Override
    public ReceiverInfResponse updateReceiver(String id, UpdateReceiverRequest updateReceiverRequest) {
        log.info("Logistics-Users-Service -> Receiver-Service -> Update-Receiver-By-ID: Update receiver with id: {}", id);

        if (receiverRepository.existsById(id)) {
            Receiver receiver = receiverMapper.toReceiver(updateReceiverRequest);
            receiver.setId(UUID.fromString(id));

            return receiverMapper.toReceiverInfResponse(receiverRepository.save(receiver));
        } else {
            log.error("Logistics-Users-Service -> Receiver-Service -> Update-Receiver-By-ID: Receiver not found with id: {}", id);
            throw new AppException(ErrorCode.RECEIVER_NOT_EXISTED);
        }
    }
}
