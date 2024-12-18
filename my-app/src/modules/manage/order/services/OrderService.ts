import http from "@/utils/http";
import { PaginatedResponse } from "../models/PaginatedResponse";
import { SuccessResponse } from "@/types/utils.type";


export async function getOrderBySenderId<T>(
    senderId: string,
    page: number = 0,
    size: number = 10
): Promise<SuccessResponse<PaginatedResponse<T>>> {
    try {
        const response = await http.get<SuccessResponse<PaginatedResponse<T>>>(
            `http://localhost:8086/order/orders/get-by-sender-id-and-status/${encodeURIComponent(senderId)}`,
            {
                params: {
                    page,
                    size,
                },
            })
        if (response.status === 200) {
            return response.data
        }
        throw new Error(`Error: ${response.status} - ${response.statusText}`);

    } catch (error) {
        throw new Error(`Failed to fetch orders: ${error}`);
    }
}
