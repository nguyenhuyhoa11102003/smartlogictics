package com.tdtu.logistics_orders_service.dto.model;

import com.tdtu.common.orders_service.enums.AddOnService;
import lombok.Data;

@Data
public class AddonServiceDTO {
    private AddOnService code;
    private String propValue;
}