package com.tdtu.logistics_warehouse_service.service.implement;

import com.tdtu.logistics_warehouse_service.dto.request.CreateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.request.UpdateWarehouseRequest;
import com.tdtu.logistics_warehouse_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_warehouse_service.exception.wrapper.NotFoundException;
import com.tdtu.logistics_warehouse_service.model.Address;
import com.tdtu.logistics_warehouse_service.model.Warehouse;
import com.tdtu.logistics_warehouse_service.repository.WarehouseRepository;
import com.tdtu.logistics_warehouse_service.service.WarehouseService;
import com.tdtu.logistics_warehouse_service.enumarators.WarehouseStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
	private final WarehouseRepository warehouseRepository;

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
		Warehouse warehouse = Warehouse.builder()
				.name(createWarehouseRequest.getName())
				.addressDetail(address.getAddressDetail())
				.phoneNumber(createWarehouseRequest.getPhoneNumber())
				.capacity(createWarehouseRequest.getCapacity())
				.status(createWarehouseRequest.getStatus())
				.address(address)
				.build();
		warehouse.setAddress(address);
		return WarehouseInfResponse.toWarehouseInfResponse(warehouseRepository.save(warehouse));
	}

	@Override
	public Optional<WarehouseInfResponse> getWareHouseById(Long id) {
		Optional<Warehouse> warehouse = warehouseRepository.findById(id);
		return warehouse.map(WarehouseInfResponse::toWarehouseInfResponse);
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
		warehouseUpdate.setAddressDetail(address.getAddressDetail());
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

	private String handleAddressDetail(String province, String ward, String commune, String street, String postalCode) {
		return province + ", " + ward + ", " + commune + ", " + street + ", " + postalCode;
	}
}
