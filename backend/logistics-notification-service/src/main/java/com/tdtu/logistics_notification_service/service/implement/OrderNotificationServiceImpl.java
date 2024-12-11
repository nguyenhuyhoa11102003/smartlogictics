package com.tdtu.logistics_notification_service.service.implement;

import com.tdtu.logistics_notification_service.configuration.BaseSender;
import com.tdtu.logistics_notification_service.dto.request.MailClientRequest;
import com.tdtu.logistics_notification_service.dto.request.Recipient;
import com.tdtu.logistics_notification_service.dto.request.Sender;
import com.tdtu.logistics_notification_service.repository.httpClient.MailClient;
import com.tdtu.logistics_notification_service.service.OrderNotificationService;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {


    private final MailClient mailClient;

    private final String apiKey;

    private final BaseSender baseSender;

    public OrderNotificationServiceImpl(
            MailClient mailClient,
            @Value("${notification.email.brevo-apikey}") String apiKey,
            BaseSender baseSender
    ) {
        this.mailClient = mailClient;
        this.apiKey = apiKey;
        this.baseSender = baseSender;
    }

    @KafkaListener(topics = "logistics-notification", groupId = "${spring.kafka.consumer.group-id}")
    public void listenToNotifications(ConsumerRecord<String, String> record) {
        String message = record.value();
        sendNotificationEmail("Notification Alert", message);
    }

    private void sendNotificationEmail(String subject, String content) {
        MailClientRequest request = MailClientRequest.builder()
                .sender(new Sender(senderName, senderEmail))
                .to(Collections.singletonList(new Recipient("User", "user@example.com"))) // Replace with actual recipients
                .subject(subject)
                .htmlContent(content)
                .textContent(content)
                .build();

        mailClient.sendEmail(brevoApiKey, request);
    }
}
