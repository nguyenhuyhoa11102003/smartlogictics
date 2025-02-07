import { useState } from "react";
import { RadioGroup } from "@headlessui/react";
import { Label } from "@/components/ui/label";

const paymentMethods = [
    { id: "online", name: "Thanh toán Online" },
    // { id: "offline", name: "Thanh toán khi nhận hàng (Tiền mặt)" },
    { id: "cod", name: "COD (Thu hộ tiền hàng)" },
];

const onlineMethods = [
    { id: "bank", name: "Chuyển khoản ngân hàng" },
    { id: "qr", name: "Thanh toán bằng QR Code" },
    { id: "e-wallet", name: "Ví điện tử (MoMo, ZaloPay, VNPay)" },
];

const PaymentOptions = ({ onConfirm }: { onConfirm: (data: any) => void }) => {
    const [amount, setAmount] = useState<number>(0);
    const [notes, setNotes] = useState<string>("");
    const [paymentMethod, setPaymentMethod] = useState<string>("offline");
    const [onlineMethod, setOnlineMethod] = useState<string>("bank");

    const [deliveryRequire, setDeliveryRequire] = useState<string>("");
    const [deliveryInstruction, setDeliveryInstruction] = useState<string>("");
    const [moreRequire, setMoreRequire] = useState<string>("");
    const [contentNote, setContentNote] = useState<string>("");

    const handleConfirm = () => {
        if (!selectedShipping) {
            alert("Vui lòng chọn phương thức vận chuyển!");
            return;
        }

        if (!paymentMethod) {
            alert("Vui lòng chọn phương thức thanh toán!");
            return;
        }
        const selectedMethod = shippingMethods.find(method => method.id === selectedShipping);
        const paymentData = {
            method: paymentMethod,
            onlineMethod: paymentMethod === "online" ? onlineMethod : null,
            amount: paymentMethod === "cod" ? amount : null,
            notes,
            deliveryRequire,
            deliveryInstruction,
            moreRequire,
            contentNote,
            serviceCode: selectedMethod ? (selectedMethod.id === 1 ? "ECONOMY" : selectedMethod.id === 2 ? "EXPRESS" : "URGENT") : null,
            // shippingCost: selectedMethod ? selectedMethod.cost : 0,
        };

        onConfirm(paymentData);
        alert("Xác nhận thông tin thanh toán thành công!");
    };

    
    const shippingMethods = [
        { id: 1, name: "Tiêu chuẩn", estimatedTime: "3-5 ngày", cost: 30000 },
        { id: 2, name: "Nhanh", estimatedTime: "1-2 ngày", cost: 50000 },
        { id: 3, name: "Hỏa tốc", estimatedTime: "Trong ngày", cost: 100000 },
    ];

    const [selectedShipping, setSelectedShipping] = useState<number | null>(null);
    const [selectedShippingCost, setSelectedShippingCost] = useState<number>(0);
    const handleShippingSelection = (id: number, cost: number) => {
        setSelectedShipping(id);
        setSelectedShippingCost(cost);
    };


    return (
        <div className="p-6 rounded-lg shadow-lg bg-white">
            <h3 className="text-lg font-semibold mb-4">THANH TOÁN</h3>

            <div className="border-2 shadow-lg p-4 rounded-lg">
                <h1 className="text-xl font-semibold mb-4">Thông Tin Hàng Hóa</h1>

                {/* Phương thức vận chuyển */}
                <div className="form-section mb-4">
                    <Label>Chọn phương thức vận chuyển:</Label>
                    <div className="border p-4 rounded-lg bg-gray-100">
                        {shippingMethods.map((method) => (
                            <div
                                key={method.id}
                                className={`flex justify-between items-center p-3 rounded-lg cursor-pointer 
                        ${selectedShipping === method.id ? "bg-blue-100 border-2 border-blue-500" : "bg-white border"}`}
                                onClick={() => handleShippingSelection(method.id, method.cost)}
                            >
                                <div>
                                    <p className="font-semibold">{method.name}</p>
                                    <p className="text-gray-600 text-sm">Thời gian dự kiến: {method.estimatedTime}</p>
                                </div>
                                <p className="font-bold text-blue-600">
                                    {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(method.cost)}
                                </p>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Chọn phương thức thanh toán */}
            <div className="mb-4">
                <label className="block font-semibold mb-2">Chọn phương thức thanh toán</label>
                <RadioGroup value={paymentMethod} onChange={setPaymentMethod}>
                    <div className="flex flex-col gap-2">
                        {paymentMethods.map((method) => (
                            <RadioGroup.Option
                                key={method.id}
                                value={method.id}
                                className={({ checked }) =>
                                    `p-2 border rounded-lg cursor-pointer ${checked ? "bg-blue-500 text-white" : "bg-white"
                                    }`
                                }
                            >
                                {method.name}
                            </RadioGroup.Option>
                        ))}
                    </div>
                </RadioGroup>
            </div>

            {/* Nếu chọn thanh toán online, hiển thị các phương thức Online */}
            {paymentMethod === "online" && (
                <div className="mb-4">
                    <label className="block font-semibold mb-2">Chọn phương thức Online</label>
                    <RadioGroup value={onlineMethod} onChange={setOnlineMethod}>
                        <div className="flex flex-col gap-2">
                            {onlineMethods.map((method) => (
                                <RadioGroup.Option
                                    key={method.id}
                                    value={method.id}
                                    className={({ checked }) =>
                                        `p-2 border rounded-lg cursor-pointer ${checked ? "bg-blue-500 text-white" : "bg-white"
                                        }`
                                    }
                                >
                                    {method.name}
                                </RadioGroup.Option>
                            ))}
                        </div>
                    </RadioGroup>
                </div>
            )}

            {/* Nếu chọn COD, hiển thị nhập số tiền thu hộ */}
            {paymentMethod === "cod" && (
                <div className="mb-4">
                    <label htmlFor="amount" className="block font-semibold">Thu hộ bằng tiền hàng</label>
                    <input
                        id="amount"
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(Number(e.target.value))}
                        className="w-full p-2 border rounded"
                    />
                </div>
            )}

            {/* Ghi chú đơn hàng */}
            <div className="mb-4">
                <label className="block font-semibold">Ghi chú</label>
                <textarea
                    value={notes}
                    onChange={(e) => setNotes(e.target.value)}
                    placeholder="Nhập ghi chú"
                    className="w-full p-2 border rounded"
                />
            </div>

            {/* Yêu cầu giao hàng */}
            <div className="mb-4">
                <label className="block font-semibold">Yêu cầu giao hàng</label>
                <textarea
                    value={deliveryRequire}
                    onChange={(e) => setDeliveryRequire(e.target.value)}
                    placeholder="Nhập yêu cầu giao hàng"
                    className="w-full p-2 border rounded"
                />
            </div>

            {/* Hướng dẫn giao hàng */}
            <div className="mb-4">
                <label className="block font-semibold">Hướng dẫn giao hàng</label>
                <textarea
                    value={deliveryInstruction}
                    onChange={(e) => setDeliveryInstruction(e.target.value)}
                    placeholder="Nhập hướng dẫn giao hàng"
                    className="w-full p-2 border rounded"
                />
            </div>

            {/* Các yêu cầu khác */}
            <div className="mb-4">
                <label className="block font-semibold">Các yêu cầu khác</label>
                <textarea
                    value={moreRequire}
                    onChange={(e) => setMoreRequire(e.target.value)}
                    placeholder="Nhập yêu cầu khác"
                    className="w-full p-2 border rounded"
                />
            </div>

            {/* Nội dung ghi chú */}
            <div className="mb-4">
                <label className="block font-semibold">Nội dung ghi chú</label>
                <textarea
                    value={contentNote}
                    onChange={(e) => setContentNote(e.target.value)}
                    placeholder="Nhập nội dung ghi chú"
                    className="w-full p-2 border rounded"
                />
            </div>

            {/* Nút Xác nhận */}
            <button
                onClick={handleConfirm}
                className="w-full bg-green-500 text-white p-2 rounded-lg hover:bg-green-600 transition"
            >
                Xác nhận thanh toán
            </button>
        </div>
    );
};

export default PaymentOptions;
