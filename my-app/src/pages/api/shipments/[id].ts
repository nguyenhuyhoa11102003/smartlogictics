import { Shipment } from '@/modules/shipment/models/Shipment';
import { NextApiRequest, NextApiResponse } from 'next';
import ShipmentStatus from '@/modules/shipment/models/ShipmentStatus';

export default function handler(
    req: NextApiRequest,
    res: NextApiResponse<Shipment>) {

    const shipment: Shipment = {
        "id": 783,
        "trackingNumber": "TN9845",
        "shipper": 887,
        "shipmentMethod": "road",
        "fromWarehouse": {
            "id": 1,
            "name": "Warehouse A",
            "warehouseType": "Storage",
            "address": "2 Some St, City, Country",
            "region": "East",
            "phoneNumber": "555-0001",
            "status": "Inactive"
        },
        "intermediateWarehouses": [
            {
                "id": 2,
                "name": "Warehouse B",
                "warehouseType": "Sorting",
                "address": "3 Some St, City, Country",
                "region": "South",
                "phoneNumber": "555-0002",
                "status": "Active"
            },
            {
                "id": 3,
                "name": "Warehouse C",
                "warehouseType": "Storage",
                "address": "4 Some St, City, Country",
                "region": "North",
                "phoneNumber": "555-0003",
                "status": "Inactive"
            }
        ],
        "toWarehouse": {
            "id": 4,
            "name": "Warehouse D",
            "warehouseType": "Receiving",
            "address": "5 Some St, City, Country",
            "region": "East",
            "phoneNumber": "555-0004",
            "status": "Active"
        },
        "shipmentStatus": ShipmentStatus.IN_TRANSIT,
        "shipmentStartDate": "2024-12-10T00:00:00",
        "estimatedDeliveryDate": "2024-12-15T00:00:00",
        "orders": ["ORD-21", "ORD-22"],
        "originName": "City A",
        "destination": "City B",
        "shipmentWeight": 542,
        "shipmentVolume": 32,
        "vehicle": {
            "id": 5,
            "name": "Vehicle 1",
            "employee": {
                "id": 9,
                "name": "Employee 3",
                "role": "Driver"
            }
        }
    }
    res.status(200).json(shipment);
};
