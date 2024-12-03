package com.tdtu.logistics_orders_service.constant;

import com.tdtu.logistics_orders_service.entity.OrderStatus;
import com.tdtu.logistics_orders_service.enumrator.StatusCategory;

public class PredefinedOrderStatus {

    public static final OrderStatus RECEIVED = OrderStatus.builder()
            .status("Đã tiếp nhận")
            .description("The order has been received.")
            .category(StatusCategory.WAITING_FOR_PICKUP)
            .build();

    public static final OrderStatus PICKING = OrderStatus.builder()
            .status("Đang lấy hàng")
            .description("The order is being picked up.")
            .category(StatusCategory.WAITING_FOR_PICKUP)
            .build();

    public static final OrderStatus PICKED = OrderStatus.builder()
            .status("Đã lấy hàng")
            .description("The order has been picked up.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus TRANSPORTING = OrderStatus.builder()
            .status("Đang vận chuyển")
            .description("The order is being transported.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus DELIVERING = OrderStatus.builder()
            .status("Đang giao hàng")
            .description("The order is being delivered.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus RE_DELIVERY = OrderStatus.builder()
            .status("Chờ phát lại")
            .description("The order is waiting for re-delivery.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus DELIVERED = OrderStatus.builder()
            .status("Giao thành công")
            .description("The order has been successfully delivered.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus PROCESSING = OrderStatus.builder()
            .status("Chờ xử lý")
            .description("The order is waiting to be processed.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus DRAFT = OrderStatus.builder()
            .status("Đơn nháp")
            .description("The order is in draft status.")
            .category(StatusCategory.DRAFT_ORDER)
            .build();

    public static final OrderStatus FAILED_PICKUP = OrderStatus.builder()
            .status("Tồn-Lấy hàng không thành công")
            .description("Failed to pick up the order.")
            .category(StatusCategory.WAITING_FOR_PICKUP)
            .build();

    public static final OrderStatus APPROVED_RETURN = OrderStatus.builder()
            .status("Đã duyệt hoàn")
            .description("The order return has been approved.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus RETURNED = OrderStatus.builder()
            .status("Đã trả")
            .description("The order has been returned.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus CANCELLED_DELIVERY = OrderStatus.builder()
            .status("Đã hủy giao")
            .description("The delivery has been cancelled.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus RETURNING = OrderStatus.builder()
            .status("Đang chuyển hoàn")
            .description("The order is being returned.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus CONTINUED_DELIVERY = OrderStatus.builder()
            .status("Phát tiếp")
            .description("The delivery is continued.")
            .category(StatusCategory.SUCCESSFUL_PICKUP)
            .build();

    public static final OrderStatus VTP_CANCEL_PICKUP = OrderStatus.builder()
            .status("VTP hủy lấy")
            .description("VTP cancelled the pickup.")
            .category(StatusCategory.PICKUP_CANCELLED)
            .build();

    private PredefinedOrderStatus() {
        // Prevent instantiation
    }
}
