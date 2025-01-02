package com.tdtu.logistics_shipments_service.service.client.fallback;

import com.tdtu.logistics_shipments_service.service.client.DeliveryServiceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class DeliveryServiceFallback implements DeliveryServiceFeignClient {

	@Override
	public ResponseEntity<?> getRoutes(String origin, String destination, String returnSummary, String transportMode) {
		return null;
	}
}
