package com.tdtu.logistics_inventory_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
	Long id;
	Long warehouseId;
	String warehouseName;
	String productName;
	int quantity;
	double weight;
	double length;
	double width;
	double height;
	double price;
	String image;
	String typePackage;

}
