package com.tdtu.common.orders_service.enums;

public enum ShippingMethod {
    ROAD("Chuyển phát đường bộ"),
    E_COMMERCE("TMĐT"),
    AIR_E_COMMERCE("TMĐT-Đường bay");

    private final String description; // Mô tả phương thức vận chuyển

    ShippingMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}