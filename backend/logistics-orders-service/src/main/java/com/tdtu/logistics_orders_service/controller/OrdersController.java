package com.tdtu.logistics_orders_service.controller;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.entity.Orders;
import com.tdtu.logistics_orders_service.enumrator.OrderStatus;
import com.tdtu.logistics_orders_service.service.OrdersService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersController {

	OrdersService ordersService;

	@PostMapping(value = "/create",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderInfResponse> createRecipient(@RequestBody @Valid CreateOrderRequest requestDTO) {

		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.CREATED.value())
				.message("Create sender successfully")
				.result(ordersService.createOrder(requestDTO))
				.build();
	}

	@PutMapping(value = "/update-status/{orderId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderInfResponse> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus orderStatus) {

		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Update order status successfully")
				.result(ordersService.updateOrderStatus(orderId, orderStatus))
				.build();
	}

	@GetMapping(value = "/get/{orderId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderInfResponse> getOrderById(@PathVariable String orderId) {

		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Get order by id successfully")
				.result(ordersService.getOrderById(orderId))
				.build();
	}

	@GetMapping(value = "/get-by-sender-id-and-status/{senderId}/{status}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<List<OrderInfResponse>> getOrderBySenderIdAndStatus(@PathVariable String senderId,
																		   @PathVariable OrderStatus status) {

		return ApiResponse.<List<OrderInfResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Get order by sender id and status successfully")
				.result(ordersService.getOrderBySenderIdAndStatus(senderId, status))
				.build();
	}

}
