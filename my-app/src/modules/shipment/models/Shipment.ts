import { Warehouse } from "@/modules/warehouse/models/Warehouse";
import ShipmentStatus from "./ShipmentStatus";

interface Shipment {
    id?: number;
    trackingNumber: string;
    shipper: number;
    shipmentMethod: string;
    fromWarehouse: Warehouse;
    intermediateWarehouses: Warehouse[];
    toWarehouse: Warehouse;
    shipmentStatus: ShipmentStatus;
    shipmentStartDate: string;
    estimatedDeliveryDate: string;
    actualDeliveryDate?: string;
    originName: string;
    destination: string;
    orders: string[]; //  danh sách các đơn hàng
    shipmentWeight: number;
    shipmentVolume: number;
    vehicle: {
        id: number,
        name: string,
        employee: { id: number, name: string, role: string }
    }
}

export type { Shipment };
