package com.tdtu.logistics_notification_service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "notification.sender")
public class BaseSender {
    protected String name;
    protected String email;
}

