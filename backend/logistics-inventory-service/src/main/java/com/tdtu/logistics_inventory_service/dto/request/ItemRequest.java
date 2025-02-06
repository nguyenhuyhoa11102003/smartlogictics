package com.tdtu.logistics_inventory_service.dto.request;

import com.tdtu.logistics_inventory_service.enumrator.EnumPackage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemRequest {
	@NotNull(message = "Tên sản phẩm không được để trống")
	private String productName;

	@Min(value = 1, message = "Số lượng phải lớn hơn 0")
	private int quantity;

	@Min(value = 0, message = "Cân nặng không hợp lệ")
	private double weight;

	private double length;
	private double width;
	private double height;

	@NotNull(message = "Loại sản phẩm không được để trống")
	private EnumPackage productType = EnumPackage.PARCEL;
}
