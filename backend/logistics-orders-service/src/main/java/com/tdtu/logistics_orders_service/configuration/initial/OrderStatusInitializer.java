package com.tdtu.logistics_orders_service.configuration.initial;

import com.tdtu.logistics_orders_service.constant.PredefinedOrderStatus;
import com.tdtu.logistics_orders_service.entity.OrderStatus;
import com.tdtu.logistics_orders_service.repository.OrderStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusInitializer implements CommandLineRunner {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusInitializer(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (orderStatusRepository.count() == 0) {
            // Thêm các trạng thái đơn hàng từ PredefinedOrderStatus vào cơ sở dữ liệu
            List<OrderStatus> predefinedStatuses = List.of(
                    PredefinedOrderStatus.RECEIVED,
                    PredefinedOrderStatus.PICKING,
                    PredefinedOrderStatus.PICKED,
                    PredefinedOrderStatus.TRANSPORTING,
                    PredefinedOrderStatus.DELIVERING,
                    PredefinedOrderStatus.RE_DELIVERY,
                    PredefinedOrderStatus.DELIVERED,
                    PredefinedOrderStatus.PROCESSING,
                    PredefinedOrderStatus.DRAFT,
                    PredefinedOrderStatus.FAILED_PICKUP,
                    PredefinedOrderStatus.APPROVED_RETURN,
                    PredefinedOrderStatus.RETURNED,
                    PredefinedOrderStatus.CANCELLED_DELIVERY,
                    PredefinedOrderStatus.RETURNING,
                    PredefinedOrderStatus.CONTINUED_DELIVERY,
                    PredefinedOrderStatus.VTP_CANCEL_PICKUP
            );

            // Lưu các trạng thái vào cơ sở dữ liệu
            orderStatusRepository.saveAll(predefinedStatuses);
        }
    }
}
