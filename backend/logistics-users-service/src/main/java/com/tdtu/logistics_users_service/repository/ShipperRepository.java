package com.tdtu.logistics_users_service.repository;

import com.tdtu.logistics_users_service.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, UUID> {

    Optional<Shipper> findByEmployeeCode(String employeeCode);

}
