import React, { useEffect, useState } from 'react';
import { Truck, Sun, Cloud, CloudRain, AlertTriangle } from 'lucide-react';
import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import { Shipment } from '@/modules/shipment/models/Shipment';

interface Props {
    shipment: Shipment;
  }

const ShipmentTracking = ({ shipment }: Props) => {
    const [segments, setSegments] = useState([
        // {
        //     id: 1,
        //     from: "Kho Hà Nội",
        //     to: "Kho Đà Nẵng",
        //     status: 'completed',
        //     plannedStart: "2024-12-10T08:00",
        //     plannedDuration: 15, // hours
        //     actualStart: "2024-12-10T08:00",
        //     actualDuration: 17,
        //     weather: "sunny",
        //     traffic: "normal",
        //     notes: ""
        // },
        // {
        //     id: 2,
        //     from: "Kho Đà Nẵng",
        //     to: "Kho HCM",
        //     status: 'in_progress',
        //     plannedStart: "2024-12-11T01:00",
        //     plannedDuration: 12,
        //     actualStart: "2024-12-11T01:00",
        //     actualDuration: null,
        //     weather: "rainy",
        //     traffic: "heavy",
        //     notes: "Mưa lớn, đường đông"
        // },
        // {
        //     id: 3,
        //     from: "Kho HCM",
        //     to: "Kho Cần Thơ",
        //     status: 'pending',
        //     plannedStart: "2024-12-11T13:00",
        //     plannedDuration: 4,
        //     actualStart: null,
        //     actualDuration: null,
        //     weather: "cloudy",
        //     traffic: "normal",
        //     notes: ""
        // }
    ]);

    const formatDateTime = (dateTime: string | null) => {
        if (!dateTime) return '-';
        const date = new Date(dateTime);
        return date.toLocaleString('vi-VN'); // Format as per your locale
    };

    const formatDuration = (hours: number | null) => {
        if (hours == null) return '-';
        return `${hours} giờ`;
    };

    const getWeatherIcon = (weather: string) => {
        switch (weather) {
            case 'sunny': return <Sun className="w-5 h-5 text-yellow-500" />;
            case 'cloudy': return <Cloud className="w-5 h-5 text-gray-500" />;
            case 'rainy': return <CloudRain className="w-5 h-5 text-blue-500" />;
            default: return null;
        }
    };


    const getTrafficColor = (traffic: string) => {
        switch (traffic) {
            case 'light': return 'text-green-500';
            case 'normal': return 'text-yellow-500';
            case 'heavy': return 'text-red-500';
            default: return 'text-gray-500';
        }
    };

    const updateSegment = (id: number) => {
        setSegments((prevSegments) =>
            prevSegments.map((segment) =>
                segment.id === id
                    ? { ...segment, status: segment.status === 'in_progress' ? 'completed' : 'in_progress' }
                    : segment
            )
        );
    };

    useEffect(() => {       
        // setSegments(shipment.shipmentSegments);

        setSegments(pre =>  [ ...pre ,  ...shipment.shipmentSegments.map((segment) => ({
            id: segment.id,
            from: segment.fromWarehouse.name,
            to: segment.toWarehouse.name,   
            status: segment.segmentStatus,
            plannedStart: segment.departureTime,
            plannedDuration: 15, // hours
            actualStart: segment.arrivalTime,
            actualDuration: 17,
            weather: "sunny",
            traffic: "normal",
            notes: ""
        }))]);

        

    }, [shipment]); 

    return (
        <Card className="w-full pt-4 bg-white border-white">
            <CardHeader>
                <CardTitle className="flex items-center gap-2">
                    <Truck className="w-6 h-6" />
                    Theo dõi lộ trình vận chuyển
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="space-y-6">
                    {segments.map((segment, index) => (
                        <div key={segment.id} className="relative">
                            {/* Kết nối các segment bằng đường thẳng */}
                            {index < segments.length - 1 && (
                                <div className="absolute left-6 top-full w-0.5 h-6 bg-gray-300" />
                            )}

                            <div className={`p-4 rounded-lg border   ${segment.status === 'completed' ? 'bg-green-200 border-green-200' :
                                segment.status === 'in_progress' ? 'bg-blue-50 border-blue-200' :
                                    'bg-gray-50 border-gray-200'
                                }`}>
                                {/* Header */}
                                <div className="flex justify-between items-center mb-4">
                                    <div className="font-medium">{segment.from} → {segment.to}</div>
                                    <button
                                        onClick={() => updateSegment(segment.id)}
                                        className={`px-3 py-1 rounded-full text-sm ${segment.status === 'completed' ? 'bg-green-100 text-green-700' :
                                            segment.status === 'in_progress' ? 'bg-blue-100 text-blue-700' :
                                                'bg-gray-100 text-gray-700'
                                            }`}
                                    >
                                        {segment.status === 'completed' ? 'Hoàn thành' :
                                            segment.status === 'in_progress' ? 'Đang chạy' :
                                                'Chưa bắt đầu'}
                                    </button>
                                </div>

                                {/* Thời gian */}
                                <div className="grid grid-cols-2 gap-4 mb-4">
                                    <div>
                                        <div className="text-sm text-gray-500 mb-1">Kế hoạch</div>
                                        <div className="grid grid-cols-2 gap-2">
                                            <div>
                                                <div className="text-sm font-medium">Bắt đầu</div>
                                                <div>{formatDateTime(segment.plannedStart)}</div>
                                            </div>
                                            <div>
                                                <div className="text-sm font-medium">Thời gian</div>
                                                <div>{formatDuration(segment.plannedDuration)}</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <div className="text-sm text-gray-500 mb-1">Thực tế</div>
                                        <div className="grid grid-cols-2 gap-2">
                                            <div>
                                                <div className="text-sm font-medium">Bắt đầu</div>
                                                <div>{formatDateTime(segment.actualStart)}</div>
                                            </div>
                                            <div>
                                                <div className="text-sm font-medium">Thời gian</div>
                                                <div>{formatDuration(segment.actualDuration)}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* Điều kiện */}
                                <div className="flex items-center gap-6">
                                    <div className="flex items-center gap-2">
                                        {getWeatherIcon(segment.weather)}
                                        <span className="capitalize">{segment.weather}</span>
                                    </div>
                                    <div className={`flex items-center gap-2 ${getTrafficColor(segment.traffic)}`}>
                                        <AlertTriangle className="w-5 h-5" />
                                        <span>
                                            {segment.traffic === 'light' ? 'Thông thoáng' :
                                                segment.traffic === 'normal' ? 'Bình thường' :
                                                    'Đông đúc'}
                                        </span>
                                    </div>
                                </div>

                                {/* Ghi chú */}
                                {segment.notes && (
                                    <div className="mt-3 text-sm text-gray-600">
                                        {segment.notes}
                                    </div>
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            </CardContent>
        </Card>
    );
};

export default ShipmentTracking;
