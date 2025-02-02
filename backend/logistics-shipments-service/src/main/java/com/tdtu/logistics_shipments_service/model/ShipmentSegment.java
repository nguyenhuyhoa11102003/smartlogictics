package com.tdtu.logistics_shipments_service.model;

import com.tdtu.logistics_shipments_service.enumrator.SegmentStatus;
import com.tdtu.logistics_shipments_service.enumrator.WeatherCondition;
import com.tdtu.logistics_shipments_service.enumrator.TrafficCondition;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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

	@Column(name = "from_warehouse_id", nullable = false)
	Long fromWarehouseId;

	@Column(name = "to_warehouse_id", nullable = false)
	Long toWarehouseId;

	@Column(name = "departure_time", nullable = false)
	LocalDateTime departureTime;

	@Column(name = "arrival_time", nullable = false)
	LocalDateTime arrivalTime;

	@Column(name = "stopover_time", nullable = false)
	float stopoverDuration;

	@Enumerated(EnumType.STRING)
	WeatherCondition weatherCondition = WeatherCondition.CLEAR;

	@Enumerated(EnumType.STRING)
	TrafficCondition trafficCondition = TrafficCondition.LIGHT;

	@Enumerated(EnumType.STRING)
	SegmentStatus segmentStatus = SegmentStatus.NOT_STARTED;

	@Column(name = "notes", columnDefinition = "TEXT")
	String notes;

	@Column(name = "summary_duration", nullable = false)
	float summaryDuration;

	@Column(name = "summary_length", nullable = false)
	float summaryLength;

	@Column(name = "summary_base_duration", nullable = false)
	float summaryBaseDuration;

	@Column(name = "isHoliday", nullable = false)
	boolean isHoliday = Boolean.FALSE;
}
