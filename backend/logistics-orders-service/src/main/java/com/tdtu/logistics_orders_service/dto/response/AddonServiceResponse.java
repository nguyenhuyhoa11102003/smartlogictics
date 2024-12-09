package com.tdtu.logistics_orders_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AddonServiceResponse {
    private String code; // Mã dịch vụ bổ sung
    private Integer fee; // Phí dịch vụ bổ sung
    private Integer feeBeforeTax; // Phí dịch vụ trước thuế
    private Integer tax; // Thuế
    private Integer taxRate; // Tỷ lệ thuế (%)
    private List<AddonServicePropResponse> propList; // Danh sách thuộc tính bổ sung
}