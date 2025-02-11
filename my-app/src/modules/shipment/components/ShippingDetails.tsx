import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { Truck, Calendar, Package, MapPin } from "lucide-react";
import { Shipment } from '@/modules/shipment/models/Shipment';
import ShipmentTracking from './ShipmentTracking';

interface DetailItemProps {
  icon: React.ComponentType<React.SVGProps<SVGSVGElement>>;
  title: string; 
  value?: string; 
}
interface Props {
  shipment: Shipment;
}

const DetailItem = ({ icon: Icon, title, value }: DetailItemProps) => (
  <div className="flex items-start gap-3">
    <div className="mt-1">
      <Icon className="h-5 w-5 text-gray-500" />
    </div>
    <div>
      <h5 className="font-medium text-gray-700">{title}</h5>
      <p className="text-gray-600">{value || "N/A"}</p>
    </div>
  </div>
);

const ShippingDetails = ({ shipment }: Props) => {
  if (!shipment) {
    return (
      <div className="container mx-auto px-4 py-6">
        <Card className="bg-white">
          <CardHeader>
            <CardTitle className="text-2xl text-center">Chi Tiết Vận Chuyển</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-center text-gray-500">Không có thông tin vận chuyển</p>
          </CardContent>
        </Card>
      </div>
    );
  }

  // Safely format dates
  const formatDate = (dateString: string) => {
    try {
      return new Date(dateString).toLocaleDateString();
    } catch (error) {
      return "N/A";
    }
  };

  // Safely get intermediate warehouses
  const getIntermediateWarehouses = () => {
    if (!shipment.intermediateWarehouses || !Array.isArray(shipment.intermediateWarehouses)) {
      return "N/A";
    }
    return shipment.intermediateWarehouses.map(warehouse => warehouse?.name + "-" + warehouse?.address || "N/A").join(', ') || "N/A";
  };

  return (
    <div className="container mx-auto px-4 py-6">
      <Card className="bg-white">
        <CardHeader>
          <CardTitle className="text-2xl text-center">Chi Tiết Vận Chuyển</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Primary Tracking Info */}
            <div className="space-y-4 md:border-r md:pr-6">
              <DetailItem
              
              icon={Package}
                title="Mã theo dõi"
                value={shipment.trackingNumber}
              />
              <DetailItem
                icon={Truck}
                title="Phương thức vận chuyển"
                value={shipment.shipmentMethod}
              />
            </div>

            {/* Warehouse Info */}
            <div className="space-y-4 md:pl-6">
              <DetailItem
                icon={MapPin}
                title="Kho xuất phát"
                value={shipment.fromWarehouse?.name   + " - " + shipment.fromWarehouse?.address}  
              />
              <DetailItem
                icon={MapPin}
                title="Kho đích"
                value={shipment.toWarehouse?.name  + " - " + shipment.toWarehouse?.address}
              />
            </div>
          </div>

          <Separator className="my-6" />

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Dates */}
            <div className="space-y-4">
              <DetailItem
                icon={Calendar}
                title="Ngày bắt đầu"
                value={shipment.departureTime}
              />
              <DetailItem
                icon={Calendar}
                title="Ngày dự kiến"
                value={shipment.arrivalTime}
              />
            </div>

            {/* Status and Intermediate */}
            <div className="space-y-4">
              <DetailItem
                icon={Truck}
                title="Trạng thái"
                value={shipment.shipmentStatus}
              />
              <DetailItem
                icon={MapPin}
                title="Kho trung gian"
                value={getIntermediateWarehouses()}
              />
            </div>
          </div>

          <Separator className="my-6" />

          {/* Vehicle and Staff Info */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <DetailItem
              icon={Truck}
              title="Xe vận chuyển"
              value={shipment.vehicle?.name}
            />
            <DetailItem
              icon={Truck}
              title="Thành viên xe"
              value={`${shipment.vehicle?.employee?.name || "N/A"} (${shipment.vehicle?.employee?.role || "N/A"})`}
            />
          </div>

          <ShipmentTracking shipment ={shipment} />
        </CardContent>
      </Card>
    </div>
  );
};

export default ShippingDetails;