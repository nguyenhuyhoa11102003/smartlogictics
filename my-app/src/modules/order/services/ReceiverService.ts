import { CreateReceiverRequest } from "../models/CreateReceiverRequest";
import { ReceiverInfResponse } from "../models/ReceiverInfResponse";

export const sendReceiverData = async (
    receiverData: CreateReceiverRequest
): Promise<ReceiverInfResponse> => {
    try {
        const response = await fetch("http://localhost:8082/users/receiver/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(receiverData),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const responseData = await response.json();
        const result: ReceiverInfResponse = responseData.data.result;
        return result;
    } catch (error) {
        console.error("Error sending receiver data:", error);
        throw error; // Re-throw the error to handle it in the calling code
    }
};
