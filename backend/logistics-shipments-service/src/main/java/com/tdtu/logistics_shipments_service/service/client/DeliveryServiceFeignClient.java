package com.tdtu.logistics_shipments_service.service.client;


import com.tdtu.logistics_shipments_service.config.feignClient.ClientConfig;
import com.tdtu.logistics_shipments_service.service.client.fallback.DeliveryServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
		value = "delivery-service",
		url = "${delivery-service.url}",
		configuration = ClientConfig.class,
		fallback = DeliveryServiceFallback.class
)
public interface DeliveryServiceFeignClient {
	@RequestMapping(value = "/delivery/deliver/routes", method = RequestMethod.GET)
	ResponseEntity<?> getRoutes(@RequestParam("origin") String origin,
	                            @RequestParam("destination") String destination,
	                            @RequestParam(value = "return_summary" ,  defaultValue = "summary") String returnSummary  ,
	                            @RequestParam(value = "transport_mode" , defaultValue = "car") String transportMode);

}
