package com.tdtu.logistics_users_service.dto.request;


import com.tdtu.logistics_users_service.entity.Address;
import com.tdtu.logistics_users_service.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignRequest {
	String orderId; // Mã đơn hàng.
	String shipperId; // Mã nhân viên giao hàng.
	Address address; // Địa chỉ giao hàng.
	String note; // Ghi chú.
	String province; // Tỉnh.
	String provinceCode; // Mã tỉnh.
	String district; // Huyện.
	String districtCode; // Mã huyện.
	String ward;     // Xã/Phường.
	String wardCode; // Mã xã/phường.
	String street;   // Đường.
	String postalCode; // Mã bưu chính.
	String warehouseId; // Mã kho hàng.
	float latitude; // Vĩ độ.
	float longitude; // Kinh độ.
}
