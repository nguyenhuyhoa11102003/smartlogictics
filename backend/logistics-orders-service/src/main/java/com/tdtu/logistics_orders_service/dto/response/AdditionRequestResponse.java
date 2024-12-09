package com.tdtu.logistics_orders_service.dto.response;

import lombok.Data;

import java.util.List;

/**
 * DTO cho phản hồi thông tin yêu cầu bổ sung
 */
@Data
public class AdditionRequestResponse {
    private String code; // Mã yêu cầu bổ sung
    private Integer fee; // Phí yêu cầu bổ sung
    private Integer feeBeforeTax; // Phí yêu cầu trước thuế
    private Integer tax; // Thuế
    private Integer taxRate; // Tỷ lệ thuế (%)
    private List<AddonServicePropResponse> propList; // Danh sách thuộc tính yêu cầu bổ sung
}