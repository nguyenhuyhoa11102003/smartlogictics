import { Checkout } from '../models/Checkout';
import { EOrderStatus } from '../models/EOrderStatus';
import { Order } from '../models/Order';
import { OrderGetVm } from '../models/OrderGetVm';

export async function createOrder(order: Order): Promise<Order | null> {
  const response = await fetch('/api/order/storefront/orders', {
    method: 'POST',
    body: JSON.stringify(order),
    headers: { 'Content-type': 'application/json; charset=UTF-8' },
  });
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  return Promise.reject(response.status);
}

export async function getMyOrders(
  productName: string,
  orderStatus: EOrderStatus | null
): Promise<OrderGetVm[]> {
  const res = await fetch(
    `/api/order/storefront/orders/my-orders?productName=${productName}&orderStatus=${
      orderStatus ?? ''
    }`
  );
  if (res.status >= 200 && res.status < 300) return res.json();
  return Promise.reject(res);
}

export async function createCheckout(checkout: Checkout): Promise<Checkout | null> {
  const response = await fetch('http://localhost:8087/api/order/storefront/checkouts', {
    method: 'POST',
    body: JSON.stringify(checkout),
    headers: { 'Content-type': 'application/json; charset=UTF-8' },
  });
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  return Promise.reject(response.status);
}

export async function getCheckoutById(id: string) {
  const response = await fetch('/api/order/storefront/checkouts/' + id, {
    method: 'GET',
    headers: { 'Content-type': 'application/json; charset=UTF-8' },
  });
  if (response.status >= 200 && response.status < 300) return response.json();
  return Promise.reject(response.status);
}
