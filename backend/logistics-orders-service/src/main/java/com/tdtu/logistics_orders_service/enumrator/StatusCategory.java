package com.tdtu.logistics_orders_service.enumrator;

public enum StatusCategory {
    SUCCESSFUL_PICKUP("Lấy hàng thành công"),
    WAITING_FOR_PICKUP("Chờ lấy"),
    PICKUP_CANCELLED("Hủy lấy"),
    DRAFT_ORDER("Đơn nháp");

    private final String description;

    StatusCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
