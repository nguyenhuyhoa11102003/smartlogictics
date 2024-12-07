package com.tdtu.logistics_notification_service.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Recipient {
    private String name;

    private String email;
}