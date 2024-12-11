package com.tdtu.logistics_orders_service.utils;

import com.tdtu.logistics_orders_service.enumrator.OrderStatus;

public class OrderStatusValidator {

    public static boolean inValid(OrderStatus orderStatus) {
        return (orderStatus == OrderStatus.CANCELLED_DELIVERY || orderStatus == OrderStatus.VTP_CANCEL_PICKUP || orderStatus == OrderStatus.APPROVED_RETURN);
    }
}
