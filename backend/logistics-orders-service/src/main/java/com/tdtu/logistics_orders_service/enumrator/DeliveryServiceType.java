package com.tdtu.logistics_orders_service.enumrator;

public enum DeliveryServiceType {
    ECONOMY("TMĐT Tiết Kiệm", 5000),
    EXPRESS("TMĐT Nhanh", 10000),
    URGENT_SCHEDULED("Hỏa tốc, hẹn giờ", 100000);

    private final String description;
    private final int price;

    DeliveryServiceType(String description, int price) {
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
