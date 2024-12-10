package com.tdtu.common.user_service.enums;

public enum VehicleType {
    MOTORBIKE("Xe máy"),
    TRUCK("Xe tải");

    private final String name;

    VehicleType(String value) {
        this.name = value;
    }

    public String getValue() {
        return name;
    }
}
