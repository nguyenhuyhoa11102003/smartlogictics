package com.tdtu.logistics_orders_service.service.implement;

import com.tdtu.logistics_orders_service.dto.request.CreateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.request.UpdateRecipientRequestDTO;
import com.tdtu.logistics_orders_service.dto.response.RecipientInfResponseDTO;
import com.tdtu.logistics_orders_service.entity.Recipient;
import com.tdtu.logistics_orders_service.exception.AppException;
import com.tdtu.logistics_orders_service.exception.ErrorCode;
import com.tdtu.logistics_orders_service.mapper.RecipientMapper;
import com.tdtu.logistics_orders_service.repository.RecipientRepository;
import com.tdtu.logistics_orders_service.service.RecipientService;
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
public class RecipientServiceImpl implements RecipientService {

    RecipientRepository recipientRepository;

    RecipientMapper recipientMapper;

    @Transactional
    @Override
    public RecipientInfResponseDTO createRecipient(CreateRecipientRequestDTO createRecipientRequestDTO) {
        log.info("Recipient Service: Creating Recipient name: {}, phone : {}",
                createRecipientRequestDTO.getFullName(),
                createRecipientRequestDTO.getPhoneNumber());

        return recipientMapper.toRecipientInfResponseDTO(recipientRepository.save(recipientMapper.toRecipient(createRecipientRequestDTO)));
    }

    @Transactional
    @Override
    public RecipientInfResponseDTO updateRecipient(String id, UpdateRecipientRequestDTO updateRecipientRequestDTO) {
        log.info("Recipient Service: Updating Recipient: {}", id);

        if (!recipientRepository.existsById(id)) {
            log.error("Recipient Service: Update Recipient: Recipient not found: {}", id);
            throw new AppException(ErrorCode.RECIPIENT_NOT_FOUND);
        } else {
            Recipient recipient = recipientMapper.toRecipient(updateRecipientRequestDTO);
            recipient.setId(id);

            log.info("Recipient Service: Recipient updated successfully: {}", id);
            return recipientMapper.toRecipientInfResponseDTO(recipientRepository.save(recipientMapper.toRecipient(updateRecipientRequestDTO)));
        }

    }

    @Override
    public RecipientInfResponseDTO getRecipientById(String id) {
        log.info("Recipient Service: Get Recipient By Id: {}", id);

        Recipient recipient = recipientRepository.findById(id).orElseThrow(() -> {
            log.error("Recipient Service: Get Recipient By Id: Recipient not found:  {}", id);
            return new AppException(ErrorCode.RECIPIENT_NOT_FOUND);
        });

        return recipientMapper.toRecipientInfResponseDTO(recipient);
    }

    @Override
    public RecipientInfResponseDTO getRecipientByEmail(String email) {
        log.info("Recipient Service: Get Recipient By Email: {}", email);

        Recipient recipient = recipientRepository.findByEmail(email).orElseThrow(() -> {
            log.error("Recipient Service: Get Recipient By Email: Recipient not found: {}", email);
            return new AppException(ErrorCode.RECIPIENT_NOT_FOUND);
        });

        return recipientMapper.toRecipientInfResponseDTO(recipient);
    }

    @Transactional
    @Override
    public void deleteRecipientById(String id) {
        log.info("Recipient Service: Deleting Recipient ID: {}", id);

        if (!recipientRepository.existsById(id)) {
            log.error("Recipient Service: Delete Recipient: Recipient not found: {}", id);
            throw new AppException(ErrorCode.RECIPIENT_NOT_FOUND);
        } else {
            recipientRepository.deleteById(id);
            log.info("Recipient Service: Recipient deleted successfully: {}", id);
        }
    }
}
