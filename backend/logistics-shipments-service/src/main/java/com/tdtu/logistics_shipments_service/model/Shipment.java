package com.tdtu.logistics_shipments_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Shipment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "from_warehouse_id", nullable = false)
	private Long fromWarehouseId; // ID của kho xuất phát

	@ElementCollection
	@CollectionTable(name = "shipment_intermediate_warehouses", joinColumns = @JoinColumn(name = "shipment_id"))
	@Column(name = "warehouse_id")
	private List<Long> intermediateWarehouseIds; // Danh sách các kho trung gian (chỉ lưu trữ ID)

	@Column(name = "to_warehouse_id")
	private Long toWarehouseId; // ID của kho đích

	@Column(name = "shipment_status")
	private String shipmentStatus; // Trạng thái vận chuyển (ví dụ: "pending", "in transit", "delivered")

	@Column(name = "shipment_start_date")
	private LocalDateTime shipmentStartDate; // Ngày bắt đầu vận chuyển

	@Column(name = "estimated_delivery_date")
	private LocalDateTime estimatedDeliveryDate; // Ngày dự kiến giao hàng

	@Column(name = "delivery_address")
	private String deliveryAddress; // Địa chỉ giao hàng (nếu có)

	@Column(name = "shipping_cost")
	private BigDecimal shippingCost; // Phí vận chuyển (nếu có)

	@Column(name = "tracking_number")
	private String trackingNumber; // Thêm các thông tin liên quan đến việc theo dõi (tracking)

}
