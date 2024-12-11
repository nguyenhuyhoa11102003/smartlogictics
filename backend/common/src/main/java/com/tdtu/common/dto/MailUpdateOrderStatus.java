package com.tdtu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailUpdateOrderStatus {
    //Customer's name created in user profile
    private String subject;

    //Customer's email created in user profile
    private String to;

    //The content of the email
    private String contents;

    @Builder.Default
    private String htmlContent = """
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f4f4f4;
                        color: #333;
                    }
                    .email-container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        background-color: #007BFF;
                        color: #ffffff;
                        padding: 20px;
                        text-align: center;
                        font-size: 24px;
                        font-weight: bold;
                    }
                    .content {
                        padding: 20px;
                        font-size: 16px;
                        line-height: 1.6;
                    }
                    .footer {
                        background-color: #f4f4f4;
                        text-align: center;
                        padding: 10px;
                        font-size: 14px;
                        color: #999;
                    }
                    .footer a {
                        color: #007BFF;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        Update Order Status
                    </div>
                    <div class="content">
                        <p>Dear {{customerName}},</p>
                        <p>We would like to inform you that the status of your order <strong>{{orderId}}</strong> has been updated to:</p>
                        <p><strong>{{orderStatus}}</strong></p>
                        <p>Thank you for choosing our service. If you have any questions, please feel free to contact us.</p>
                        <p>Best regards,<br>The Logistic Smart Team</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 Logistic Smart. All rights reserved.</p>
                        <p><a href="https://www.logisticsmart.com">Visit our website</a></p>
                    </div>
                </div>
            </body>
            </html>
        """;

}
