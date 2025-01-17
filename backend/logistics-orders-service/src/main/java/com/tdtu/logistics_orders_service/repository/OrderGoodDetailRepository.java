package com.tdtu.logistics_orders_service.repository;

import com.tdtu.logistics_orders_service.entity.OrderGoodDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OrderGoodDetailRepository extends JpaRepository<OrderGoodDetail, String> {
}
