"use client";

import { Checkbox } from "@/components/ui/checkbox";
import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";
import { useEffect, useRef, useState } from "react";
import { Order } from "@/modules/order/models/Order";
import { getOrderBySenderId } from "../services/OrderService";
import { PaginatedResponse } from "../models/PaginatedResponse";
import { OrderInfResponse } from "../models/OrderInfResponse";
import { useAuth } from "@/context/app.context";
import { SuccessResponse } from "@/types/utils.type";
import { formatToVietnamTime } from "@/utils/utils";
import { OrderStatus } from "@/modules/order/models/EOrderStatus";
import Link from "next/link";

const statusColors: Record<OrderStatus, string> = {
    "Đã tiếp nhận": "bg-yellow-200 text-yellow-800",
    "Đang lấy hàng": "bg-yellow-200 text-yellow-800",
    "Đã lấy hàng": "bg-yellow-200 text-yellow-800",
    "Đang vận chuyển": "bg-blue-200 text-blue-800",
    "Đang giao hàng": "bg-orange-200 text-orange-800",
    "Chờ phát lại": "bg-red-200 text-red-800",
    "Giao thành công": "bg-green-200 text-green-800",
    "Chờ xử lý": "bg-gray-200 text-gray-800",
};

const mapStatusToDisplayName = (status: string): string => {
    const displayNameMapping: Record<string, string> = {
        PENDING: "Chờ xử lý",
        APPROVED: "Đã duyệt",
        REJECTED: "Bị từ chối",
        COMPLETED: "Hoàn thành",
        CANCELLED: "Đã hủy",
        RECEIVED: "Đã tiếp nhận"
    };

    return displayNameMapping[status] || "Không xác định";
};

export function OrdersTable() {
    const [orders, setOrders] = useState<PaginatedResponse<OrderInfResponse> | null>(null);
    // Save temorary after that use UseContext
    const [senderId, setSenderId] = useState<string>("9f034bde-ff6b-4cf9-88be-13f24fe0bf25");
    const [contextMenuPosition, setContextMenuPosition] = useState<{ x: number; y: number } | null>(null);
    const [selectedItem, setSelectedItem] = useState<string | null>(null);

    const handleRightClick = (event: React.MouseEvent, itemId: string) => {
        event.preventDefault();
        setContextMenuPosition({ x: event.clientX, y: event.clientY });
        setSelectedItem(itemId);
    };

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const fetchedOrders: SuccessResponse<PaginatedResponse<OrderInfResponse>> | null = await getOrderBySenderId(senderId);
                if (fetchedOrders.result) {
                    setOrders(fetchedOrders.result)
                }

            } catch (error) {
                console.error(error);
            }
        };
        fetchOrders()
    }, [senderId])

    const handleEdit = () => {
        if (selectedItem) {
            alert(`Edit order with ID: ${selectedItem}`);
        }
        setContextMenuPosition(null); // Close the context menu
    };

    const handleDelete = () => {
        if (selectedItem) {
            alert(`Delete order with ID: ${selectedItem}`);
        }
        setContextMenuPosition(null); // Close the context menu
    };


    // Create a reference for the context menu
    const contextMenuRef = useRef<HTMLDivElement | null>(null);

    // Close the context menu if clicked outside
    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (contextMenuRef.current && !contextMenuRef.current.contains(event.target as Node)) {
                setContextMenuPosition(null); // Close the context menu if clicked outside
            }
        };

        // Attach the event listener
        document.addEventListener("mousedown", handleClickOutside);

        // Clean up the event listener
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);


    return (
        <div className="overflow-auto border rounded-md">
            <table className="min-w-full bg-white border border-gray-200">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="p-2 border">Chọn</th>
                        <th className="p-2 border">Thao tác</th>
                        <th className="p-2 border">Mã vận đơn</th>
                        <th className="p-2 border">Mã đơn hàng</th>
                        <th className="p-2 border">Người gửi</th>
                        <th className="p-2 border">Người nhận</th>
                        <th className="p-2 border">Hàng hóa</th>
                        <th className="p-2 border">Trạng thái</th>
                        <th className="p-2 border">Ngày lập</th>
                        <th className="p-2 border">Tổng cước</th>
                        <th className="p-2 border">IN/CHƯA IN</th>
                    </tr>
                </thead>
                <tbody>
                    {orders?.content.map((item) => (
                        <tr key={item.id}
                            className="hover:bg-gray-50 border-t"
                        >
                            <td className="p-2 border text-center">
                                <Checkbox />
                            </td>
                            <td className="p-2 border text-center"
                                onClick={(e) => handleRightClick(e, item.id)}>
                                Thao tác
                            </td>
                            <td className="p-2 border flex flex-col">
                                {/* Shipment ID with blue color */}
                                <span className="text-green-500 ">{item.shipmentId}</span>

                                {/* Link to order details */}
                                <Link href={`/order-details/${item.shipmentId}`} className="text-blue-500 text-right">
                                    Xem chi tiết
                                </Link>
                            </td>
                            <td className="p-2 border">{item.orderHdrID}</td>
                            <td className="p-2 border">{item.senderName}</td>
                            <td className="p-2 border">{item.receiverName}</td>
                            <td className="p-2 border">{"Tên hàng hóa"}</td>
                            <td className="p-2 border">
                                <Badge className={cn(item.status)}>
                                    {mapStatusToDisplayName(item.status)}
                                </Badge>
                            </td>
                            <td className="p-2 border">{formatToVietnamTime(item.createdDate ?? "2024-12-10T22:37:36.199276")}</td>
                            <td className="p-2 border">{`12.000 đ`}</td>
                            <td className="p-2 border">
                                <Badge className={cn(item.status)}>
                                    {`Đã in`}
                                </Badge>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Context Menu */}
            {contextMenuPosition && (
                <div
                    ref={contextMenuRef} // Attach the ref here
                    className="absolute z-10 bg-white border rounded-md shadow-md p-2"
                    style={{
                        top: contextMenuPosition.y,
                        left: contextMenuPosition.x,
                    }}
                >
                    <ul>
                        <li>
                            <button
                                className="px-3 py-1 text-sm text-blue-600 hover:text-blue-800"
                                onClick={handleEdit}
                            >
                                Sửa
                            </button>
                        </li>
                        <li>
                            <button
                                className="px-3 py-1 text-sm text-red-600 hover:text-red-800"
                                onClick={handleDelete}
                            >
                                Xóa
                            </button>
                        </li>
                    </ul>
                </div>
            )}
        </div>
    );
}
