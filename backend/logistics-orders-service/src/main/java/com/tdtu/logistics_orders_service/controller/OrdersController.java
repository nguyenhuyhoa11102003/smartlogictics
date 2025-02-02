package com.tdtu.logistics_orders_service.controller;

import com.tdtu.logistics_orders_service.dto.request.CreateOrderRequest;
import com.tdtu.logistics_orders_service.dto.request.PickupRequest;
import com.tdtu.logistics_orders_service.dto.request.DeliveryRequest;
import com.tdtu.logistics_orders_service.dto.response.ApiResponse;
import com.tdtu.logistics_orders_service.dto.response.OrderInfResponse;
import com.tdtu.logistics_orders_service.dto.response.PaginatedResponse;
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
	public ApiResponse<OrderInfResponse> createOrder(@RequestBody @Valid CreateOrderRequest requestDTO) {
		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.CREATED.value())
				.message("Create sender successfully")
				.result(ordersService.createOrder(requestDTO))
				.build();
	}

	@PostMapping(value = "/create-multiple",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<Void> createMultipleOrders(@RequestBody @Valid List<CreateOrderRequest> requestList) {
		ordersService.createMultipleOrders(requestList);

		return ApiResponse.<Void>builder()
				.code(HttpStatus.CREATED.value())
				.message("Create multiple orders successfully")
				.build();
	}

	@PutMapping(value = "/{branchCode}/update-status/{orderId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderInfResponse> updateOrderStatus(
			@PathVariable String branchCode,
			@PathVariable String orderId,
			@RequestParam OrderStatus orderStatus) {

		String message = ordersService.updateOrderStatus(branchCode, orderId, orderStatus) ?
				"Update order status successfully" : "Update order status failed";

		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message(message)
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

	@GetMapping(value = "/get-by-sender-id-and-status/{senderId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<PaginatedResponse<OrderInfResponse>> getOrderBySenderId(
			@PathVariable("senderId") String senderId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		return (ApiResponse.<PaginatedResponse<OrderInfResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("Get order by sender id successfully")
				.result(ordersService.getOrderBySenderId(senderId, page, size))
				.build());
	}

	// Post: phân công nhận đơn hàng cho shipper
	@PostMapping(value = "/assign/pick-up/{orderId}/{shipperId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderInfResponse> assignShipperToOrder(
			@PathVariable("orderId") String orderId,
			@PathVariable("shipperId") String shipperId,
			@RequestBody PickupRequest pickupRequest
	) {
		OrderInfResponse orderInfResponse = ordersService.assignShipperPickUp(orderId, shipperId, pickupRequest);
		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Assign shipper to order successfully")
				.result(orderInfResponse)
				.build();
	}


	// POST xử lý giao hàng
	@PostMapping(value = "/assign/deliver/{orderId}/{shipperId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderInfResponse> deliverOrder(
			@PathVariable("orderId") String orderId,
			@PathVariable("shipperId") String shipperId,
			@RequestBody DeliveryRequest deliveryRequest) {

		OrderInfResponse orderInfResponse = ordersService.assignShipperDelivery(orderId, shipperId, deliveryRequest);
		return ApiResponse.<OrderInfResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Order delivered successfully")
				.result(orderInfResponse)
				.build();
	}
}
