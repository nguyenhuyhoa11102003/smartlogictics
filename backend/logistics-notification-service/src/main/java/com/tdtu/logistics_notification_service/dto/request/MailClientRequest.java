package com.tdtu.logistics_notification_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.internals.Sender;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailClientRequest {

    private Sender sender;

    private List<Recipient> to;

    private String subject;

    private String htmlContent;

    private String textContent;
}