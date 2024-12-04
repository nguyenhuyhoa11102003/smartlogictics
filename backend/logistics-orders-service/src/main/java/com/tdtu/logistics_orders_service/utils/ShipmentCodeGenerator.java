package com.tdtu.logistics_orders_service.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ShipmentCodeGenerator {
    private static final int CODE_LENGTH = 13;

    private ShipmentCodeGenerator() {
        // Private constructor để ngăn việc khởi tạo class
    }

    public static String generateShipmentCode() {
        // Lấy thời gian hiện tại làm một phần của mã
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));

        // Sinh thêm số ngẫu nhiên để đạt độ dài mong muốn
        int remainingLength = CODE_LENGTH - timestamp.length();
        String randomDigits = generateRandomDigits(remainingLength);

        return timestamp + randomDigits;
    }

    private static String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder digits = new StringBuilder();

        for (int i = 0; i < length; i++) {
            digits.append(random.nextInt(10)); // Sinh số từ 0-9
        }

        return digits.toString();
    }
}
