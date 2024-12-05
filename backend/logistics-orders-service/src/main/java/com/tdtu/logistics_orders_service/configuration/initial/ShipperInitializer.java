package com.tdtu.logistics_orders_service.configuration.initial;

import com.tdtu.logistics_orders_service.entity.Shipper;
import com.tdtu.logistics_orders_service.repository.ShipperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShipperInitializer implements CommandLineRunner {

    private final ShipperRepository shipperRepository;

    public ShipperInitializer(ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Chỉ thêm dữ liệu nếu bảng `shippers` rỗng
        if (shipperRepository.count() == 0) {
            // Danh sách Shipper mặc định
            List<Shipper> predefinedShippers = List.of(
                    Shipper.builder()
                            .fullName("Nguyen Van Ha") // Đại diện Hà Nội
                            .phoneNumber("0123456789")
                            .email("nguyenvanha@example.com")
                            .vehicleNumber("29A-12345")
                            .build(),

                    Shipper.builder()
                            .fullName("Tran Thi Thanh") // Đại diện Thanh Hóa
                            .phoneNumber("0987654321")
                            .email("tranthithanh@example.com")
                            .vehicleNumber("36B-67890")
                            .build(),

                    Shipper.builder()
                            .fullName("Le Van Hoang") // Đại diện Hồ Chí Minh
                            .phoneNumber("0901234567")
                            .email("levanhoang@example.com")
                            .vehicleNumber("59C-11223")
                            .build()
            );

            // Lưu vào cơ sở dữ liệu
            shipperRepository.saveAll(predefinedShippers);
        }
    }
}