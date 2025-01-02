package com.tdtu.logistics_shipments_service.service;


import com.tdtu.logistics_shipments_service.dto.request.ShipmentActualDataDTO;
import com.tdtu.logistics_shipments_service.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TrackingService {
	ShipmentRepository shipmentRepository;

	@Transactional
	public void updateShipmentActualData(ShipmentActualDataDTO actualData) {

	}

}
