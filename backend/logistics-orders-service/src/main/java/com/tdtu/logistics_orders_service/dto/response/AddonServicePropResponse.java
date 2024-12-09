package com.tdtu.logistics_orders_service.dto.response;

import lombok.Data;

/**
 * DTO cho phản hồi thuộc tính của dịch vụ bổ sung
 */
@Data
public class AddonServicePropResponse {
    private String propCode; // Mã thuộc tính
    private String propValue; // Giá trị thuộc tính
    private String propValueActual; // Giá trị thực tế (có thể null)
}