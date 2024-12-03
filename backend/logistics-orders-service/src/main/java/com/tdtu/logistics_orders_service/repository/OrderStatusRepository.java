package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, String> {
}
