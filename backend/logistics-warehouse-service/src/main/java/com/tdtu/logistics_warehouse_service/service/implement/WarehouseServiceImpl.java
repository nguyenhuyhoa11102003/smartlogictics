package com.tdtu.logistics_warehouse_service.service.implement;

import com.tdtu.logistics_warehouse_service.dto.request.CreateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.request.UpdateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.response.ApiResponse;
import com.tdtu.logistics_warehouse_service.dto.response.CoordinatesResponse;
import com.tdtu.logistics_warehouse_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_warehouse_service.exception.wrapper.NotFoundException;
import com.tdtu.logistics_warehouse_service.model.Address;
import com.tdtu.logistics_warehouse_service.model.Warehouse;
import com.tdtu.logistics_warehouse_service.repository.WarehouseRepository;
import com.tdtu.logistics_warehouse_service.service.WarehouseService;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import com.tdtu.logistics_warehouse_service.service.client.DeliveryServiceFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
	WarehouseRepository warehouseRepository;
	DeliveryServiceFeignClient deliveryServiceFeignClient;

	@Transactional
	@Override
	public WarehouseInfResponse createWarehouse(CreateWarehouseRequest createWarehouseRequest) {
		Address address = Address.builder()
				.province(createWarehouseRequest.getAddress().getProvince())
				.ward(createWarehouseRequest.getAddress().getWard())
				.commune(createWarehouseRequest.getAddress().getCommune())
				.street(createWarehouseRequest.getAddress().getStreet())
				.postalCode(createWarehouseRequest.getAddress().getPostalCode())
				.addressDetail(handleAddressDetail(
						createWarehouseRequest.getAddress().getProvince(),
						createWarehouseRequest.getAddress().getWard(),
						createWarehouseRequest.getAddress().getCommune(),
						createWarehouseRequest.getAddress().getStreet(),
						createWarehouseRequest.getAddress().getPostalCode()))
				.build();

		CoordinatesResponse coordinatesResponse = deliveryServiceFeignClient
				.get_coordinates(address.getAddressDetail()).getResult();

		address.setLongitude(coordinatesResponse.getLongitude());
		address.setLatitude(coordinatesResponse.getLatitude());

		Warehouse warehouse = Warehouse.builder()
				.name(createWarehouseRequest.getName())
				.phoneNumber(createWarehouseRequest.getPhoneNumber())
				.capacity(createWarehouseRequest.getCapacity())
				.status(createWarehouseRequest.getStatus())
				.address(address)
				.build();

		Warehouse createdWarehouse = warehouseRepository.saveAndFlush(warehouse);
		return getWareHouseById(createdWarehouse.getId())
				.orElseThrow(() -> new NotFoundException("Warehouse not found"));
	}

	@Override
	public Optional<WarehouseInfResponse> getWareHouseById(Long id) {
		Optional<Warehouse> warehouse = Optional.ofNullable(
				warehouseRepository.findById(id).orElseThrow(
						() -> new NotFoundException("Warehouse not found")
				));
		return warehouse.map(WarehouseInfResponse::toWarehouseInfResponse);
	}

	@Override
	public List<WarehouseInfResponse> getWareHouseByIds(List<Long> ids) {
		List<WarehouseInfResponse> warehouseInfResponses = new ArrayList<>();

		ids.forEach(id -> {
			Optional<Warehouse> warehouse = Optional.ofNullable(
					warehouseRepository.findById(id).orElseThrow(
							() -> new NotFoundException("Warehouse not found")
					));
			warehouse.ifPresent(value -> warehouseInfResponses.add(WarehouseInfResponse.toWarehouseInfResponse(value)));
		});

		return warehouseInfResponses;
	}

	@Transactional
	@Override
	public WarehouseInfResponse updateWarehouse(UpdateWarehouseRequest createWarehouseRequest, Long id) {
		Warehouse warehouseUpdate = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException("Warehouse not found"));
		Address address = Address.builder()
				.province(createWarehouseRequest.getAddress().getProvince())
				.ward(createWarehouseRequest.getAddress().getWard())
				.commune(createWarehouseRequest.getAddress().getCommune())
				.street(createWarehouseRequest.getAddress().getStreet())
				.postalCode(createWarehouseRequest.getAddress().getPostalCode())
				.addressDetail(handleAddressDetail(
						createWarehouseRequest.getAddress().getProvince(),
						createWarehouseRequest.getAddress().getWard(),
						createWarehouseRequest.getAddress().getCommune(),
						createWarehouseRequest.getAddress().getStreet(),
						createWarehouseRequest.getAddress().getPostalCode()))
				.build();

		warehouseUpdate.setName(createWarehouseRequest.getName());
		warehouseUpdate.setPhoneNumber(createWarehouseRequest.getPhoneNumber());
		warehouseUpdate.setCapacity(createWarehouseRequest.getCapacity());
		warehouseUpdate.setStatus(createWarehouseRequest.getStatus());
		warehouseUpdate.setAddress(address);
		return WarehouseInfResponse.toWarehouseInfResponse(warehouseRepository.save(warehouseUpdate));
	}


	@Transactional
	@Override
	public WarehouseInfResponse deleteWarehouse(Long id) {
		Warehouse warehouseUpdate = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException("Warehouse not found"));
		warehouseUpdate.setStatus(WarehouseStatus.CLOSED);
		return WarehouseInfResponse.toWarehouseInfResponse(warehouseRepository.save(warehouseUpdate));
	}

	@Override
	public List<WarehouseInfResponse> getAllWarehouses(int pageNo, int pageSize) {
		Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
		Page<Warehouse> warehouses = warehouseRepository.findAll(pageable);
		List<WarehouseInfResponse> warehouseInfResponses = new ArrayList<>();
		warehouses.getContent().forEach(warehouse -> {
			warehouseInfResponses.add(WarehouseInfResponse.toWarehouseInfResponse(warehouse));
		});
		return warehouseInfResponses;
	}

	private String handleAddressDetail(String province, String ward, String commune, String street, String postalCode) {
		return street + ", " + commune + ", " + ward + ", " + province + ", " + postalCode;
	}
}
