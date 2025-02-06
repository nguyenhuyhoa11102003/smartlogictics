package com.tdtu.logistics_inventory_service.dto.request;

import com.tdtu.logistics_inventory_service.enumrator.EnumPackage;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class AddInventoryRequest {

	@NotNull(message = "Warehouse ID không được để trống")
	private Long warehouseId;

	@NotNull(message = "Tên sản phẩm không được để trống")
	private EnumPackage productType;  // "Bưu kiện"

	private String productName;  // lấy tên mã đơn hàng luôn

	@NotNull(message = "Danh sách hàng hóa không được để trống")
	private List<ItemRequest> items;
}
