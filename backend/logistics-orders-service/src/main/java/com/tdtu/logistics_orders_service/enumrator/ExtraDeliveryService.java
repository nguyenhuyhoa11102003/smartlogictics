package com.tdtu.logistics_orders_service.enumrator;

public enum ExtraDeliveryService {
    DIRECT_HANDOVER("Phát tận tay", 10000),
    EXCHANGE("Đổi hàng", 20000),
    DECLARE_VALUE("Khai Giá", 15000),
    SMS_NOTIFICATION("SMS thông tin đơn hàng", 5000),
    COD_VIEW("Thu tiền xem hàng", 25000),
    PARTIAL_DELIVERY("Giao một phần", 30000);

    private final String description;
    private final int price;

    ExtraDeliveryService(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
