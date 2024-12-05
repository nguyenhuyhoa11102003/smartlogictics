package com.tdtu.logistics_orders_service.controller;


import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequestDTO;
import com.tdtu.logistics_orders_service.service.OrderService;
import com.tdtu.logistics_orders_service.viewmodel.order.OrderPostVm;
import com.tdtu.logistics_orders_service.viewmodel.order.OrderVm;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
	OrderService orderService;

	// create new order
	@PostMapping("/create")
	public ResponseEntity<OrderVm> createOrder(
			@Valid @RequestBody OrderPostVm orderPostVm) {
		OrderVm orderVm = orderService.createOrder(orderPostVm);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderVm);
	}


	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello");
	}
}
