package com.tdtu.logistics_orders_service.enumrator;

public enum OrderStatus {
    RECEIVED("Đã tiếp nhận", "The order has been received.", StatusCategory.WAITING_FOR_PICKUP),
    PICKING("Đang lấy hàng", "The order is being picked up.", StatusCategory.WAITING_FOR_PICKUP),
    PICKED("Đã lấy hàng", "The order has been picked up.", StatusCategory.SUCCESSFUL_PICKUP),
    TRANSPORTING("Đang vận chuyển", "The order is being transported.", StatusCategory.SUCCESSFUL_PICKUP),
    DELIVERING("Đang giao hàng", "The order is being delivered.", StatusCategory.SUCCESSFUL_PICKUP),
    RE_DELIVERY("Chờ phát lại", "The order is waiting for re-delivery.", StatusCategory.SUCCESSFUL_PICKUP),
    DELIVERED("Giao thành công", "The order has been successfully delivered.", StatusCategory.SUCCESSFUL_PICKUP),
    PROCESSING("Chờ xử lý", "The order is waiting to be processed.", StatusCategory.SUCCESSFUL_PICKUP),
    DRAFT("Đơn nháp", "The order is in draft status.", StatusCategory.DRAFT_ORDER),
    FAILED_PICKUP("Tồn-Lấy hàng không thành công", "Failed to pick up the order.", StatusCategory.WAITING_FOR_PICKUP),
    APPROVED_RETURN("Đã duyệt hoàn", "The order return has been approved.", StatusCategory.SUCCESSFUL_PICKUP),
    RETURNED("Đã trả", "The order has been returned.", StatusCategory.SUCCESSFUL_PICKUP),
    CANCELLED_DELIVERY("Đã hủy giao", "The delivery has been cancelled.", StatusCategory.SUCCESSFUL_PICKUP),
    RETURNING("Đang chuyển hoàn", "The order is being returned.", StatusCategory.SUCCESSFUL_PICKUP),
    CONTINUED_DELIVERY("Phát tiếp", "The delivery is continued.", StatusCategory.SUCCESSFUL_PICKUP),
    VTP_CANCEL_PICKUP("VTP hủy lấy", "VTP cancelled the pickup.", StatusCategory.PICKUP_CANCELLED);

    private final String status;
    private final String description;
    private final StatusCategory category;

    OrderStatus(String status, String description, StatusCategory category) {
        this.status = status;
        this.description = description;
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public StatusCategory getCategory() {
        return category;
    }
}