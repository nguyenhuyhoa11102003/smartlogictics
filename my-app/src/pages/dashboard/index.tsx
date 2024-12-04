import LayoutDashboard from "@/components/LayoutDashboard";
import {
    Tabs,
    TabsContent,
    TabsList,
    TabsTrigger,
} from "@/components/ui/tabs"

import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import PickupForm from "@/components/PickupForm";
import ProductForm from "@/components/ProductForm";
import CODForm from "@/components/CODFrom";
import { Checkbox } from "@/components/ui/checkbox";
import { useState } from "react";
import { useMutation, UseMutationResult } from '@tanstack/react-query';

interface FormData {
    name: string;
    email: string;
}

interface ResponseData {
    message: string;
    data: {
        name: string;
        email: string;
    };
}

export default function Dashboard() {
    const [formData, setFormData] = useState<FormData>({ name: '', email: '' });

    // Mutation để gửi dữ liệu đến API
    const mutation = useMutation<ResponseData, Error, FormData>({
        mutationFn: add
    });

    async function add(data: FormData): Promise<ResponseData> {
        const response = await fetch('/api/order', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        alert('ok')

        if (!response.ok) {
            throw new Error('Failed to submit form');
        }

        const result: ResponseData = await response.json();
        return result;

    }

    // Handle input change
    // const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    //     const { name, value } = event.target;
    //     setFormData({
    //         ...formData,
    //         [name]: value,
    //     });
    // };
    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        console.log("Form Data Submitted:", formData);
        mutation.mutate(formData); // Gửi dữ liệu
    }

    return (
        <div>
            <LayoutDashboard>
                <Tabs defaultValue="inside" className="w-full">
                    <TabsList className="grid grid-cols-2">
                        <TabsTrigger value="inside">Tạo đơn trong nước</TabsTrigger>
                        <TabsTrigger value="outside">Tạo đơn quốc tế</TabsTrigger>
                    </TabsList>
                    <TabsContent value="inside">
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-6">
                            <div className="p-6">
                                <PickupForm />
                                <div className="border-2 shadow-lg p-4 mt-4">
                                    <h2 className="font-bold text-lg mb-4">NGƯỜI NHẬN</h2>
                                    <form>
                                        <div className="mb-4">
                                            <Label>Tùy chọn nơi nhận</Label>
                                            <div className="flex gap-4">
                                                <label>
                                                    <Input type="radio" name="deliveryMethod" value="address" />
                                                    Theo địa chỉ KH nhận
                                                </label>
                                                <label>
                                                    <Input type="radio" name="deliveryMethod" value="smartBox" />
                                                    Nhận tại từ Smart Box
                                                </label>
                                                <label>
                                                    <Input type="radio" name="deliveryMethod" value="postOffice" />
                                                    Nhận tại bưu cục
                                                </label>
                                            </div>
                                        </div>

                                        <div className="mb-4">
                                            <Label htmlFor="recipientPhone">Điện thoại</Label>
                                            <Input
                                                id="recipientPhone"
                                                type="tel"
                                                placeholder="Nhập số điện thoại"
                                                className="w-full"
                                            />
                                        </div>

                                        <div className="mb-4">
                                            <Label htmlFor="recipientName">Họ tên</Label>
                                            <Input
                                                id="recipientName"
                                                type="text"
                                                placeholder="Nhập tên người nhận"
                                                className="w-full"
                                            />
                                        </div>

                                        <div className="mb-4">
                                            <Label htmlFor="recipientAddress">Địa chỉ</Label>
                                            <Input
                                                id="recipientAddress"
                                                type="text"
                                                placeholder="Nhập địa chỉ"
                                                className="w-full"
                                            />
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div className="p-6">
                                <ProductForm />
                                <CODForm />
                            </div>
                        </div>
                        <div className="border-2 shadow-lg p-6 rounded-lg flex flex-col gap-4 fixed bottom-0 left-0 right-0 z-10 bg-white">
                            <div className="flex justify-between items-center">
                                <div className="font-semibold text-lg">Tổng cước</div>
                                <div className="mx-4"></div>
                                <div className="font-semibold text-lg">Tiền thu hộ</div>
                                <div className="font-semibold text-lg">Thời gian dự kiến</div>
                                <div className="flex flex-col items-center  gap-1">
                                    <div className="">
                                        <Checkbox
                                            checked={true}
                                        // onCheckedChange={(checked: boolean) => setSaveInfo(checked)}
                                        />
                                        <Label className="ml-2">Tôi đã đọc và đồng ý với Điều khoản quy định</Label>
                                    </div>

                                    <div className=" flex gap-4">
                                        <Button onClick={handleSubmit} className="bg-blue-500 text-white">Gửi ngay</Button>
                                        <Button className="bg-yellow-500 text-white">Lưu nháp</Button>
                                        <Button className="bg-gray-500 text-white">Làm mới</Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </TabsContent>
                    <TabsContent value="outside">
                    </TabsContent>
                </Tabs>
            </LayoutDashboard>
        </div>

    )
}