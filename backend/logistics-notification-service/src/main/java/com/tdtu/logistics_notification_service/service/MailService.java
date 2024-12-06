package com.tdtu.logistics_notification_service.service;


import com.tdtu.common.dto.MailVerifyAccount;

import com.tdtu.logistics_notification_service.dto.request.MailClientRequest;
import com.tdtu.logistics_notification_service.dto.response.MailResponse;

public interface MailService {
    MailResponse sentVerifyAccount(MailVerifyAccount mailVerifyAccount);

    MailResponse sentNotification(MailClientRequest mailRequest);
}
