import React, { useState } from "react";


// lười , nhớ làm thì làm , không thì thôi
const statusColors: Record<string, string> = {
    "RECEIVED": "bg-yellow-200 text-yellow-800",
    "Đang lấy hàng": "bg-yellow-200 text-yellow-800",
    "Đã lấy hàng": "bg-yellow-200 text-yellow-800",
    "Đang vận chuyển": "bg-blue-200 text-blue-800",
    "Đang giao hàng": "bg-orange-200 text-orange-800",
    "Chờ phát lại": "bg-red-200 text-red-800",
    "Giao thành công": "bg-green-200 text-green-800",
    "Chờ xử lý": "bg-gray-200 text-gray-800",
};

interface Order {
    id: string;
    shipmentCode: string;
    orderCode: string;
    senderName: string;
    recipientName: string;
    goods: string;
    status: string;
    createdAt: string;
    printed: boolean;
    insurance: boolean;
}

interface OrdersTableProps {
    orders: Order[];
}

export function OrdersTable({ orders }: OrdersTableProps) {
    const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);

    const onView = (order: Order) => {
        setSelectedOrder(order);
    };

    const onEdit = (order: Order) => {
        console.log("Edit order: ", order);
    };

    const onDelete = (orderId: string) => {
        console.log("Delete order with ID: ", orderId);
    };

    return (
        <div>
            {/* Bảng đơn hàng */}
            <div className="overflow-auto border rounded-md">
                <table className="min-w-full bg-white border border-gray-200">
                    <thead>
                        <tr className="bg-gray-100">
                            <th className="p-2 border">Mã vận đơn</th>
                            <th className="p-2 border">Mã đơn hàng</th>
                            <th className="p-2 border">Người gửi</th>
                            <th className="p-2 border">Người nhận</th>
                            <th className="p-2 border">Hàng hóa</th>
                            <th className="p-2 border">Trạng thái</th>
                            <th className="p-2 border">Ngày lập</th>
                            <th className="p-2 border">Tổng cước</th>
                            <th className="p-2 border">IN/CHƯA IN</th>
                            <th className="p-2 border">Hỗ trợ bảo hiểm</th>
                            <th className="p-2 border">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map((o) => (
                            <tr key={o.id} className="hover:bg-gray-50 border-t">
                                <td className="p-2 border text-center">{o.shipmentCode}</td>
                                <td className="p-2 border text-center">{o.orderCode}</td>
                                <td className="p-2 border text-center">{o.senderName}</td>
                                <td className="p-2 border text-center">{o.recipientName}</td>
                                <td className="p-2 border text-center">{o.packageType}</td>
                                <td className={`p-2 border text-center font-bold ${statusColors[o.status] || "bg-gray-200 text-gray-800"}`}>
                                    {o.status}
                                </td>
                                <td className="p-2 border text-center">{o.createdAt}</td>
                                <td className="p-2 border text-center">{o.totalCost ?? 100}</td>
                                <td className="p-2 border text-center">{o.printed ? "Đã in" : "Chưa in"}</td>
                                <td className="p-2 border text-center">{o.insurance ? "Có" : "Không"}</td>
                                <td className="p-2 border text-center flex gap-2 justify-center">
                                    <button className="px-3 py-1 text-sm text-blue-600 hover:text-blue-800" onClick={() => onView(o)}>
                                        Xem
                                    </button>
                                    <button className="px-3 py-1 text-sm text-green-600 hover:text-green-800" onClick={() => onEdit(o)}>
                                        Sửa
                                    </button>
                                    <button className="px-3 py-1 text-sm text-red-600 hover:text-red-800" onClick={() => onDelete(o.id)}>
                                        Xóa
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {/* Modal xem chi tiết đơn hàng */}
            {selectedOrder && (
                <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded-md shadow-lg w-96">
                        <h2 className="text-lg font-bold mb-4">Chi tiết đơn hàng</h2>
                        <p><strong>Mã vận đơn:</strong> {selectedOrder.shipmentCode}</p>
                        <p><strong>Mã đơn hàng:</strong> {selectedOrder.orderCode}</p>
                        <p><strong>Người gửi:</strong> {selectedOrder.senderName}</p>
                        <p><strong>Người nhận:</strong> {selectedOrder.recipientName}</p>
                        <p><strong>Hàng hóa:</strong> {selectedOrder.goods}</p>
                        <p className={`font-bold ${statusColors[selectedOrder.status] || "bg-gray-200 text-gray-800"} p-1 rounded-md`}>
                            <strong>Trạng thái:</strong> {selectedOrder.status}
                        </p>
                        <p><strong>Ngày lập:</strong> {selectedOrder.createdAt}</p>
                        <p><strong>Tổng cước:</strong> 100</p>
                        <p><strong>IN/CHƯA IN:</strong> {selectedOrder.printed ? "Đã in" : "Chưa in"}</p>
                        <p><strong>Hỗ trợ bảo hiểm:</strong> {selectedOrder.insurance ? "Có" : "Không"}</p>

                        <div className="flex justify-end mt-4">
                            <button className="px-4 py-2 bg-red-500 text-white rounded-md" onClick={() => setSelectedOrder(null)}>
                                Đóng
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
