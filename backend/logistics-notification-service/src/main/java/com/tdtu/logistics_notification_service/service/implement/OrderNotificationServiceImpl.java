package com.tdtu.logistics_notification_service.service.implement;

import com.tdtu.logistics_notification_service.configuration.BaseSender;
import com.tdtu.logistics_notification_service.repository.httpClient.MailClient;
import com.tdtu.logistics_notification_service.service.OrderNotificationService;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

}
