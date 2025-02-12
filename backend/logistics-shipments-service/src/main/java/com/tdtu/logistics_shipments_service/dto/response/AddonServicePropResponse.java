package com.tdtu.logistics_shipments_service.dto.response;

import lombok.Data;

@Data
public class AddonServicePropResponse {
    private String propCode; // Mã thuộc tính
    private String propValue; // Giá trị thuộc tính
    private String propValueActual; // Giá trị thực tế (có thể null)
}