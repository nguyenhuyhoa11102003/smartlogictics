package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

	@Modifying
	@Query("UPDATE Orders o SET o.status = :status WHERE o.id = :orderId and o.branchCode = :branchCode")
	int updateOrderStatusById(String branchCode, String orderId, OrderStatus status);

	@Query("SELECT o FROM Orders o WHERE o.id = :orderId and o.branchCode = :branchCode")
	Optional<Orders> findByOrderIdAndBranchCode(String orderId, String branchCode);

	@Override
	Optional<Orders> findById(String orderId);

	// Query mới để tìm đơn hàng theo senderId và status
	@Query("SELECT o FROM Orders o WHERE o.senderId = :senderId AND o.status = :status")
	List<Orders> findBySenderIdAndStatus(String senderId, OrderStatus status);


	@Query("SELECT o FROM Orders o WHERE o.senderId = :senderId ")
	Page<Orders> findBySenderId(String senderId, Pageable pageable);
}
