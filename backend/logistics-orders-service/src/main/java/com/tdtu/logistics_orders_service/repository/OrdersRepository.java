package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrdersRepository extends PagingAndSortingRepository<Orders, String> {

	@Modifying
	@Query("UPDATE Orders o SET o.status = :status WHERE o.id = :orderId and o.branchCode = :branchCode")
	int updateOrderStatusById(String branchCode, String orderId, OrderStatus status);

	@Query("SELECT o FROM Orders o WHERE o.id = :orderId and o.branchCode = :branchCode")
	Optional<Orders> findByOrderIdAndBranchCode(String orderId, String branchCode);

	Optional<Orders> findById(String orderId);

	// Query mới để tìm đơn hàng theo senderId và status
	@Query("SELECT o FROM Orders o WHERE o.senderId = :senderId AND o.status = :status")
	List<Orders> findBySenderIdAndStatus(String senderId, OrderStatus status);


	@Query("SELECT o FROM Orders o WHERE o.senderId = :senderId ")
	Page<Orders> findBySenderId(String senderId, Pageable pageable);

	@RestResource(exported = false)
	<S extends Orders> S save(S entity);

	@RestResource(exported = false)
	<S extends Orders> S update(S entity);

	@RestResource(exported = false)
	void delete(Orders orders);
}
