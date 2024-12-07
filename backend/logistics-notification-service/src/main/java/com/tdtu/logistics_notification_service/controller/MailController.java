package com.tdtu.logistics_notification_service.controller;

import com.tdtu.common.constant.KafkaTopic;
import com.tdtu.common.dto.MailVerifyAccount;
import com.tdtu.logistics_notification_service.dto.request.MailClientRequest;
import com.tdtu.logistics_notification_service.dto.response.ApiResponse;
import com.tdtu.logistics_notification_service.dto.response.MailResponse;
import com.tdtu.logistics_notification_service.service.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailController {
    MailService mailService;


    @KafkaListener(topics = KafkaTopic.CREATE_ACCOUNT)
    public void sentMailVerifyAccount(MailVerifyAccount mailVerifyAccount){
        log.info("Message received: {}", mailVerifyAccount.toString());

        mailService.sentVerifyAccount(mailVerifyAccount);
    }

    @PostMapping("/notification")
    public ApiResponse<MailResponse> sentNotification(
            @RequestBody MailClientRequest request){

        log.info("Sending email with content: {}", request.getTextContent());

        MailResponse response = mailService.sentNotification(request);

        log.info("Email have bean sent with response messageId: {}", response.getMessageId());

        return ApiResponse.<MailResponse>builder()
                .code(HttpStatus.OK.value())
                .isSuccess(true)
                .result(response)
                .message("Email sent successfully")
                .build();
    }
}
