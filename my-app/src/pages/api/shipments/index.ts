import { Shipment } from '@/modules/shipment/models/Shipment';
import { NextApiRequest, NextApiResponse } from 'next';
import ShipmentStatus from '@/modules/shipment/models/ShipmentStatus';

export default function handler(
    req: NextApiRequest,
    res: NextApiResponse<Shipment[]>) {
    const shipments: Shipment[] = [
        {
            id: 1,
            trackingNumber: "TN001",
            shipper: 501, // Assuming this is the shipper's ID
            shipmentMethod: "road", // Road, water, air, etc.
            fromWarehouse: {
                id: 1,
                name: "Warehouse A", // The name of the origin warehouse
                warehouseType: "Main", // Type of warehouse (could be 'Main', 'Secondary', etc.)
                address: "123 Main St, City, Country", // Address of the warehouse
                region: "North", // Region for the warehouse
                phoneNumber: "+123456789", // Warehouse contact
                status: "Active", // Warehouse status (could be 'Active', 'Inactive', etc.)
            },
            intermediateWarehouses: [
                {
                    id: 2,
                    name: "Warehouse B",
                    warehouseType: "Secondary",
                    address: "456 Side St, City, Country",
                    region: "Central",
                    phoneNumber: "+987654321",
                    status: "Active",
                },
            ],
            toWarehouse: {
                id: 3,
                name: "Warehouse C", // Destination warehouse name
                warehouseType: "Main",
                address: "789 End St, City, Country",
                region: "South",
                phoneNumber: "+1122334455",
                status: "Active",
            },
            shipmentStatus: ShipmentStatus.PENDING, // You can map it to an enum like ShipmentStatus.IN_TRANSIT
            shipmentStartDate: "2024-12-10T00:00:00",
            estimatedDeliveryDate: "2024-12-15T00:00:00",
            orders: ["ORD-001", "ORD-002"], // List of order IDs associated with this shipment
            originName: "Warehouse A", // The origin name
            destination: "Warehouse C", // The destination name
            shipmentWeight: 1500, // Weight in kg or grams, depending on your system
            shipmentVolume: 50, // Volume in cubic meters or liters
            vehicle: {
                id: 0,
                name: "",
                employee: { id: 0, name: "", role: "" }
            }
        }]
    res.status(200).json(shipments);
};
