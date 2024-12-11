import { AdditionRequest } from "./AdditionRequest";
import { AddonService } from "./AddonService";

export type InformationOrder = {
    senderId: string;            // ID of the sender
    recipientId: string;         // ID of the recipient
    serviceCode: string;         // Code for the service type (e.g., ECONOMY)
    addonService: AddonService[]; // List of additional services applied to the order
    additionRequest: AdditionRequest[]; // List of additional requests for the order
    branchCode: string;          // Branch code handling the order
    vehicle: string;             // Vehicle used for the delivery
    receivingMethod: string;     // Method of receiving (e.g., "CUSTOMER_ADDRESS")
    deliveryTime: string;        // Time of delivery
    deliveryRequire: string;     // Special requirements for the delivery
    deliveryInstruction: string; // Any special instructions for the delivery
    saleOrderCode: string;       // Sales order code associated with this order
    contentNote: string;         // Any notes about the content of the order
    weight: string;              // Weight of the package
    width: string;               // Width of the package
    length: string;              // Length of the package
    height: string;              // Height of the package
    broken: boolean;             // Flag indicating if the package is broken
}