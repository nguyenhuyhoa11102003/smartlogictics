package com.tdtu.logistics_shipments_service.model;

import com.tdtu.logistics_shipments_service.enumrator.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "shipments")
public class Shipment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String trackingNumber;  // Mã số theo dõi

	@Column(name = "order_id", nullable = false)
	private Long orderId; // ID của đơn hàng

	@Column(name = "shipper_id", nullable = false)
	private Long shipper; // ID của người vận chuyển

	@Column(name = "shipment_method", nullable = false)
	private String shipmentMethod; // Phương thức vận chuyển (ví dụ: "road", "air", "sea")

	@Column(name = "shipment_status", unique = true, length = 13)
	private String getShipmentStatus;// Trạng thái vận chuyển (ví dụ: "pending", "in transit", "delivered")

	@Column(name = "from_warehouse_id", nullable = false)
	private Long fromWarehouseId; // Kho xuất phát

	@ElementCollection
	@CollectionTable(name = "shipment_intermediate_warehouses", joinColumns = @JoinColumn(name = "shipment_id"))
	@Column(name = "warehouse_id")
	private List<Long> intermediateWarehouseIds; // Danh sách các kho trung gian

	@Column(name = "to_warehouse_id")
	private Long toWarehouseId; // Kho đích

	@Enumerated(EnumType.STRING)
	@Column(name = "shipment_status")
	private ShipmentStatus shipmentStatus; // Trạng thái vận chuyển (ví dụ: "pending", "in transit", "delivered")

	@Column(name = "shipment_start_date")
	private LocalDateTime shipmentStartDate; // Ngày bắt đầu vận chuyển

	@Column(name = "estimated_delivery_date")
	private LocalDateTime estimatedDeliveryDate; // Ngày dự kiến giao hàng

	@Column(name = "actual_delivery_date")
	private LocalDateTime actualDeliveryDate; // Ngày thực tế giao hàng

	@ElementCollection
	@Column(name = "order_id")
	@CollectionTable(name = "shipment_orders", joinColumns = @JoinColumn(name = "shipment_id"))
	private List<String> orders = new ArrayList<>(); // Danh sách các đơn hàng

	// so sánh 2 đối tượng Shipment
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Shipment)) {
			return false;
		}
		return id != null && id.equals(((Shipment) o).id);
	}

	// so sánh 2 đối tượng shipment
	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}
}
