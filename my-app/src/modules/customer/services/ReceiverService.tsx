import { CreateReceiverRequest } from "@/modules/order/models/CreateReceiverRequest";
import http from "@/utils/http";

export async function sendReceiverData(data: CreateReceiverRequest): Promise<any> {
    const response = http.post("http://localhost:8082/users/receiver/create", data);
    return response;
}

