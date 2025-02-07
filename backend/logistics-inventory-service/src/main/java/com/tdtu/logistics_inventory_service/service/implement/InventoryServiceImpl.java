package com.tdtu.logistics_inventory_service.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdtu.logistics_inventory_service.dto.request.AddInventoryRequest;
import com.tdtu.logistics_inventory_service.dto.request.ItemRequest;
import com.tdtu.logistics_inventory_service.dto.response.ApiResponse;
import com.tdtu.logistics_inventory_service.dto.response.WarehouseInfResponse;
import com.tdtu.logistics_inventory_service.dto.response.InventoryResponse;
import com.tdtu.logistics_inventory_service.enumrator.EnumPackage;
import com.tdtu.logistics_inventory_service.model.LogisticsPackage;
import com.tdtu.logistics_inventory_service.model.PackageDetail;
import com.tdtu.logistics_inventory_service.repository.PackageDetailRepository;
import com.tdtu.logistics_inventory_service.repository.PackageRepository;
import com.tdtu.logistics_inventory_service.service.InventoryService;
import com.tdtu.logistics_inventory_service.service.client.WarehouseServiceFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class InventoryServiceImpl implements InventoryService {

	PackageRepository packageRepository;
	PackageDetailRepository packageDetailRepository;
	WarehouseServiceFeignClient warehouseClient;
	ObjectMapper objectMapper = new ObjectMapper();

	// Add inventory
	@Override
	public InventoryResponse addInventory(AddInventoryRequest request) {

		ApiResponse<WarehouseInfResponse> warehouseResponses = warehouseClient.getWarehouseById(request.getWarehouseId());
		if (warehouseResponses == null)
			throw new RuntimeException("Kho không tồn tại!");
		WarehouseInfResponse warehouse = warehouseResponses.getResult();
		log.debug(" [{}] : {} ", InventoryServiceImpl.class.getName(), warehouse);

		//   total weight
		double currentWeightWarehouse = getPackageByWarehouseId(request.getWarehouseId()).stream().mapToDouble(inv -> inv.getQuantity() * inv.getWeight()).sum();

		// total volume
		double currentVolumeWarehouse = getPackageByWarehouseId(request.getWarehouseId()).stream().mapToDouble(inv -> inv.getQuantity() * (inv.getLength() * inv.getWidth() * inv.getHeight())).sum();



		double newWeight = 0;
		double newVolume = 0;

		for (ItemRequest item : request.getItems()) {
			if (EnumPackage.DOCUMENT == item.getProductType()) {
				newWeight += item.getQuantity() * 0.1;
			} else {
				newVolume += item.getQuantity() * (item.getLength() * item.getWidth() * item.getHeight());
				newWeight += item.getQuantity() * item.getWeight();
			}
		}

		if (currentWeightWarehouse + newWeight > warehouse.getWeightCapacity()) {
			throw new RuntimeException("Trọng lượng hàng vượt quá khả năng chứa của kho!");
		}

		if (newVolume + currentVolumeWarehouse > warehouse.getVolumeCapacity()) {
			throw new RuntimeException("Thể tích hàng vượt quá khả năng chứa của kho!");
		}

		// update capacity
		ApiResponse<WarehouseInfResponse> updatedWarehouseInfoResponse = warehouseClient.updateCapacity(request.getWarehouseId(), newVolume, newWeight);

		LogisticsPackage logisticsPackage = new LogisticsPackage();
		logisticsPackage.setWarehouseId(request.getWarehouseId());
		logisticsPackage.setProductName(request.getProductName());
		logisticsPackage.setQuantity(request.getItems().size());
		logisticsPackage.setTypePackage(request.getProductType());
		logisticsPackage.setLength(request.getItems().stream().mapToDouble(ItemRequest::getLength).sum());
		logisticsPackage.setWidth(request.getItems().stream().mapToDouble(ItemRequest::getWidth).sum());
		logisticsPackage.setHeight(request.getItems().stream().mapToDouble(ItemRequest::getHeight).sum());
		logisticsPackage.setWeight(newWeight);

		for (ItemRequest item : request.getItems()) {
			PackageDetail packageDetail = new PackageDetail();
			packageDetail.setLogisticsPackage(logisticsPackage);
			packageDetail.setProductName(item.getProductName());
			packageDetail.setLength(item.getLength());
			packageDetail.setWidth(item.getWidth());
			packageDetail.setHeight(item.getHeight());
			packageDetail.setWeight(item.getWeight());
			packageDetail.setQuantity(item.getQuantity());
			logisticsPackage.getItems().add(packageDetail);
		}

		LogisticsPackage createdPackageDetail =  packageRepository.save(logisticsPackage);
		return convertToInventoryResponse(createdPackageDetail);

	}

	// convert to InventoryResponse
	private InventoryResponse convertToInventoryResponse(LogisticsPackage response) {
		InventoryResponse inventoryResponse = new InventoryResponse();
		inventoryResponse.setId(response.getId());
		inventoryResponse.setWarehouseId(response.getWarehouseId());
		inventoryResponse.setProductName(response.getProductName());
		inventoryResponse.setQuantity(response.getQuantity());
		inventoryResponse.setLength(response.getLength());
		inventoryResponse.setWidth(response.getWidth());
		inventoryResponse.setHeight(response.getHeight());
		inventoryResponse.setWeight(response.getWeight());
		inventoryResponse.setTypePackage(response.getTypePackage().toString());
		return inventoryResponse;
	}

	// get warehouse by id
	private WarehouseInfResponse getWarehouseById(Long warehouseId) {
		ApiResponse<WarehouseInfResponse> warehouseResponses = warehouseClient.getWarehouseById(warehouseId);
		if (warehouseResponses == null)
			throw new RuntimeException("Kho không tồn tại!");
		return warehouseResponses.getResult();
	}

	// get inventory by warehouse id
	private List<LogisticsPackage> getPackageByWarehouseId(Long warehouseId) {
		return packageRepository.findLogisticsPackageByWarehouseId(warehouseId);
	}



}
