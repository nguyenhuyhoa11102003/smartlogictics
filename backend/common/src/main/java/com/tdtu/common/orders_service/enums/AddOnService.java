package com.tdtu.common.orders_service.enums;

public enum AddOnService {

    AIR_CARGO("Chuyển phát đường bay", "Dịch vụ chuyển phát nhanh qua đường hàng không"),
    CO_CHECK("Đồng kiểm", "Dịch vụ đồng kiểm hàng hóa khi giao"),
    COD_VIEW("Thu tiền xem hàng", "Dịch vụ thu tiền khi khách hàng kiểm tra hàng"),
    HIGH_VALUE("Hàng giá trị cao", "Dịch vụ dành cho hàng hóa có giá trị cao"),
    ELECTRONIC_NOTIFICATION("Báo phát điện tử", "Nhận thông báo phát hàng qua điện tử"),
    INSURANCE("Bảo hiểm", "Dịch vụ bảo hiểm cho hàng hóa"),
    PARTIAL_DELIVERY("Giao một phần", "Cho phép giao hàng theo từng phần"),
    PROMOTION("Khuyến mãi", "Dịch vụ áp dụng chương trình khuyến mãi");

    private final String name; // Tên dịch vụ cộng thêm
    private final String description; // Mô tả dịch vụ cộng thêm

    AddOnService(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}