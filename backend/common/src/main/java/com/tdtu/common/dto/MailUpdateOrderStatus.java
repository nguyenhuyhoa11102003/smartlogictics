package com.tdtu.common.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MailUpdateOrderStatus {
    //Customer's name created in user profile
    private String subject;

    //Customer's email created in user profile
    private String to;

    //The content of the email
    private String contents;

    private String htmlContent;
}
