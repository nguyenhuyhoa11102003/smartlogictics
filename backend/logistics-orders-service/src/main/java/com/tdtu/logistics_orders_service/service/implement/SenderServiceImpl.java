package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.dto.request.CreateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateSenderRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.SenderInfResponseDTO;
import com.tdtu.logistics_orders_service.entity.Sender;
import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import com.tdtu.logistics_orders_service.mapper.SenderMapper;
import com.tdtu.logistics_orders_service.repository.SenderRepository;
import com.tdtu.logistics_orders_service.service.SenderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SenderServiceImpl implements SenderService {

    SenderRepository senderRepository;

    SenderMapper senderMapper;

    @Override
    @Transactional
    public SenderInfResponseDTO createSender(CreateSenderRequestDTO createSenderRequestDTO) {
        log.info("SenderService: Creating Sender");

        return senderMapper.toSenderInfResponseDTO(senderRepository.save(senderMapper.toSender(createSenderRequestDTO)));
    }

    @Transactional
    @Override
    public SenderInfResponseDTO updateSender(String id, UpdateSenderRequestDTO updateSenderRequestDTO) {

        if (!senderRepository.existsById(id)) {
            log.error("SenderService: Update Sender: Sender not found");
            throw new AppException(ErrorCode.SENDER_NOT_FOUND);
        } else {
            Sender sender = senderMapper.toSender(updateSenderRequestDTO);
            sender.setId(id);

            log.info("SenderService: Sender updated successfully");
            return senderMapper.toSenderInfResponseDTO(senderRepository.save(sender));
        }
    }

    @Override
    public SenderInfResponseDTO getSenderById(String id) {

        Sender sender = senderRepository.findById(id).orElseThrow(() -> {
            log.error("SenderService: Get Sender By Id: Sender not found");
            return new AppException(ErrorCode.SENDER_NOT_FOUND);
        });

        return senderMapper.toSenderInfResponseDTO(sender);
    }

    @Override
    public SenderInfResponseDTO getSenderByEmail(String email) {
        Sender sender = senderRepository.findByEmail(email).orElseThrow(() -> {
            log.error("SenderService: Get Sender By Email: Sender not found");
            return new AppException(ErrorCode.SENDER_NOT_FOUND);
        });

        return senderMapper.toSenderInfResponseDTO(sender);
    }

    @Transactional
    @Override
    public void deleteSenderById(String id) {
        log.info("SenderService: Deleting Sender");

        if (!senderRepository.existsById(id)) {
            log.error("SenderService: Delete Sender: Sender not found");
            throw new AppException(ErrorCode.SENDER_NOT_FOUND);
        } else {
            senderRepository.deleteById(id);
            log.info("SenderService: Sender deleted successfully");
        }

    }
}
