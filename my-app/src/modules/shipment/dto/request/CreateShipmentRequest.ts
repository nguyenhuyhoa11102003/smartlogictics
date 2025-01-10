import ShipmentStatus from "../../models/ShipmentStatus";
import CreateShipmentSegmentRequest from "../request/CreateShipmentSegmentRequest" 

interface CreateShipmentRequest {
    trackingNumber: string;
    shipper: number;
    shipmentMethod: string;
    fromWarehouseId: number;
    intermediateWarehouseIds: number[];
    toWarehouseId: number;
    shipmentStatus: ShipmentStatus;
    departureTime: string
    orders: string[]; 
    shipmentSegmentRequests : CreateShipmentSegmentRequest[],
    vehicle: {
        id: number,
        name: string,
        employee: { id: number, name: string, role: string }
    }
}

export type { CreateShipmentRequest };
