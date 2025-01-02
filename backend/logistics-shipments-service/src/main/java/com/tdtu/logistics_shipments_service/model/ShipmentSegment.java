package com.tdtu.logistics_shipments_service.model;

import com.tdtu.logistics_shipments_service.enumrator.SegmentStatus;
import com.tdtu.logistics_shipments_service.enumrator.WeatherCondition;
import com.tdtu.logistics_shipments_service.enumrator.TrafficCondition;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipment_segments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ShipmentSegment extends AbstractMappedEntity implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipment_id", nullable = false)
	Shipment shipment;

	Long fromWarehouseId;
	Long toWarehouseId;

	Double plannedDuration; // Thời gian dự tính chuyến đi
	Double actualDuration; // Thời gian thực tế chuyến đi

	Double plannedStopoverDuration;  // Thời gian dự tính dừng lại tại chặng
	Double actualStopoverDuration;  // thời gian  thực tế dừng lại tại chặng

	@Enumerated(EnumType.STRING)
	WeatherCondition weatherCondition;

	@Enumerated(EnumType.STRING)
	TrafficCondition trafficCondition;

	@Enumerated(EnumType.STRING)
	SegmentStatus segmentStatus;

	String notes;



}
