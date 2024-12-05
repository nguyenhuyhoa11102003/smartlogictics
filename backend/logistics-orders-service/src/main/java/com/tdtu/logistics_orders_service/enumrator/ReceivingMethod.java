package com.tdtu.logistics_orders_service.enumrator;

public enum ReceivingMethod {
    CUSTOMER_ADDRESS("Theo địa chỉ KH nhận"),
    SMART_BOX("Nhận tại tủ Smart Box"),
    POST_OFFICE("Nhận tại bưu cục");

    private final String description;

    ReceivingMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
