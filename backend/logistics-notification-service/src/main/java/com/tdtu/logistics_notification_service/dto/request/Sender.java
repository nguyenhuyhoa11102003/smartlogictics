package com.tdtu.logistics_notification_service.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sender {
    protected String name;
    protected String email;
}
