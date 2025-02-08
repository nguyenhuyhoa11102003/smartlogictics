package com.tdtu.logistics_users_service.service.implement;

import com.tdtu.logistics_users_service.dto.request.PickupRequest;
import com.tdtu.logistics_users_service.dto.request.CreateShipperRequest;
import com.tdtu.logistics_users_service.dto.response.ShipperInfResponse;
import com.tdtu.logistics_users_service.entity.Shipper;
import com.tdtu.logistics_users_service.exception.AppException;
import com.tdtu.logistics_users_service.exception.ErrorCode;
import com.tdtu.logistics_users_service.mapper.ShipperMapper;
import com.tdtu.logistics_users_service.repository.ShipperRepository;
import com.tdtu.logistics_users_service.service.ShipperService;
import com.tdtu.logistics_users_service.utils.GenerateStaffId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipperServiceImpl implements ShipperService {
	final ShipperRepository shipperRepository;
	final ShipperMapper shipperMapper;

	@Override
	public ShipperInfResponse getShipperInfById(String id) {
		log.info("Logistics-Users-Service -> Shipper-Service -> Get-Shipper-By-ID: Get shipper by id: {}", id);

		Shipper shipper = shipperRepository.findById(id).orElseThrow(() -> {
			log.error("Logistics-Users-Service -> Shipper-Service -> Get-Shipper: Shipper not found with id: {}", id);
			return new AppException(ErrorCode.SHIPPER_NOT_EXISTED);
		});

		return shipperMapper.toShipperInfResponse(shipper);
	}

	@Override
	public ShipperInfResponse getShipperInfByStaffId(String staffId) {
		log.info("Logistics-Users-Service -> Shipper-Service -> Get-Shipper-By-Staff-ID: Get shipper by staff id: {}", staffId);

		Shipper shipper = shipperRepository.findByEmployeeCode(staffId).orElseThrow(() -> {
			log.error("Logistics-Users-Service -> Shipper-Service -> Get-Shipper: Shipper not found with staff id: {}", staffId);
			return new AppException(ErrorCode.SHIPPER_NOT_EXISTED);
		});

		return shipperMapper.toShipperInfResponse(shipper);
	}

	@Override
	public ShipperInfResponse createShipper(CreateShipperRequest createShipperRequest) {
		log.info("Logistics-Users-Service -> Shipper-Service -> Create-Shipper: Create shipper: {}", createShipperRequest.getFullName());

		Shipper shipper = shipperMapper.toShipper(createShipperRequest);

		Long departmentId = 1L; // test
		shipper.setEmployeeCode(GenerateStaffId.generate(String.valueOf(departmentId), shipper.getPosition()));

		return shipperMapper.toShipperInfResponse(shipperRepository.save(shipper));
	}

	@Override
	public Set<ShipperInfResponse> getShipperByWarehouse(String warehouseId) {
		log.info("Logistics-Users-Service -> Shipper-Service -> Get-Shipper-By-Warehouse-Id: Get shipper by id warehouse: {}", warehouseId);

		Set<Shipper> shippers = new HashSet<>(shipperRepository.findByWarehouseId(warehouseId));
		if (shippers.isEmpty()) {
			log.error("Logistics-Users-Service -> Shipper-Service -> Get-Shipper-By-Warehouse-Id: Shippers not found with warehouse id: {}", warehouseId);
			throw new AppException(ErrorCode.SHIPPER_NOT_EXISTED);
		}

		Set<ShipperInfResponse> shipperInfResponses = new HashSet<>();
		for (Shipper shipper : shippers) {
			shipperInfResponses.add(shipperMapper.toShipperInfResponse(shipper));
		}
		return shipperInfResponses;
	}
}

