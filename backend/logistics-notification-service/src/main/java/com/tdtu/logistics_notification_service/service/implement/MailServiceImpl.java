package com.tdtu.logistics_notification_service.service.implement;


import com.tdtu.common.dto.MailVerifyAccount;
import com.tdtu.logistics_notification_service.configuration.BaseSender;
import com.tdtu.logistics_notification_service.dto.request.MailClientRequest;
import com.tdtu.logistics_notification_service.dto.request.Recipient;
import com.tdtu.logistics_notification_service.dto.request.Sender;
import com.tdtu.logistics_notification_service.dto.response.MailResponse;
import com.tdtu.logistics_notification_service.service.MailService;
import feign.FeignException;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tdtu.logistics_notification_service.repository.httpClient.MailClient;


import java.util.List;

@Slf4j
@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Service
public class MailServiceImpl implements MailService {

    private final MailClient mailClient;

    private final String apiKey;

    private final BaseSender baseSender;

    public MailServiceImpl(
            MailClient mailClient,
            @Value("${notification.email.brevo-apikey}") String apiKey,
            BaseSender baseSender
    ) {
        this.mailClient = mailClient;
        this.apiKey = apiKey;
        this.baseSender = baseSender;
    }

    @Override
    public MailResponse sentVerifyAccount(MailVerifyAccount mailVerifyAccount) {
        try {
            MailClientRequest request = MailClientRequest.builder()
                    .subject(mailVerifyAccount.getSubject())
                    .sender(Sender.builder()
                            .email(baseSender.getEmail())
                            .name(baseSender.getName())
                            .build())
                    .to(List.of(Recipient.builder()
                            .email(mailVerifyAccount.getTo())
                            .build()))
                    .textContent(mailVerifyAccount.getContents())
                    .build();

            log.info("Sending verify account to subject...: {}", request.getSubject());

            return mailClient.sendEmail(apiKey, request);
        } catch (FeignException e){
            throw new AppException(ErrorCode.MAIL_SENDING_FAILED);
        }
    }

    @Override
    public MailResponse sentNotification(MailClientRequest request) {
        try {
            log.info("Sending email with content: {}", request.toString());
            return mailClient.sendEmail(apiKey, request);
        } catch (FeignException e){
            throw new AppException(ErrorCode.MAIL_SENDING_FAILED);
        }
    }
}
