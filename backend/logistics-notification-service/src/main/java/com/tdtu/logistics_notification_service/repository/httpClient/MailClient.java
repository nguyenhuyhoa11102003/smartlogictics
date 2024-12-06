package com.tdtu.logistics_notification_service.repository.httpClient;

import com.tdtu.logistics_notification_service.dto.request.MailClientRequest;
import com.tdtu.logistics_notification_service.dto.response.MailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "mail-client", url = "${notification.email.brevo-url}")
public interface MailClient {
    @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    MailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody MailClientRequest body);

}
