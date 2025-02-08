package com.tdtu.logistics_orders_service.configuration;

import com.tdtu.logistics_orders_service.entity.ShippingRate;
import com.tdtu.logistics_orders_service.enumrator.DeliveryServiceType;
import com.tdtu.logistics_orders_service.enumrator.ShippingZone;
import com.tdtu.logistics_orders_service.enumrator.TransportationType;
import com.tdtu.logistics_orders_service.repository.ShippingRateRepository;
import com.tdtu.logistics_orders_service.utils.ShippingRateCalculator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ShippingRateSeeder implements CommandLineRunner {
	private final ShippingRateRepository shippingRateRepository;

	public ShippingRateSeeder(ShippingRateRepository shippingRateRepository) {
		this.shippingRateRepository = shippingRateRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		shippingRateRepository.deleteAll();
		List<ShippingRate> shippingRates = new ArrayList<>();

		Arrays.stream(ShippingZone.values()).forEach(shippingZone -> Arrays.stream(DeliveryServiceType.values()).forEach(serviceType -> {
			Arrays.stream(TransportationType.values().clone()).forEach(transportationType -> {

				// handle không cho máy bay đi nội tỉnh và cận tỉnh
				if (transportationType == TransportationType.MAY_BAY && shippingZone != ShippingZone.LIEN_TINH) {
					return;
				}

				// handle không cho xe máy đi cận tỉnh và liên tỉnh
				if (shippingZone == ShippingZone.CAN_TINH || shippingZone == ShippingZone.LIEN_TINH) {
					if (transportationType == TransportationType.XE_MAY) {
						return;
					}
				}

				// handle không cho xe tải đi nội tỉnh
				if (shippingZone == ShippingZone.NOI_TINH && transportationType == TransportationType.XE_TAI) {
					return;
				}

				ShippingRate shippingRate = ShippingRateCalculator.generateRate(serviceType, shippingZone, transportationType);
				shippingRates.add(shippingRate);
			});
		}));

		this.shippingRateRepository.saveAll(shippingRates);
	}
}