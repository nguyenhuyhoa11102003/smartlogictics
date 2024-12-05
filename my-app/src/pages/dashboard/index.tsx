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
import RecipientForm from "@/components/RecipientForm";
import { PickupData } from "@/modules/order/models/PickUpData";
import { Address } from "@/modules/address/models/AddressModel";
import { Product } from "@/modules/catalog/models/Product";
interface ProductData {
    productName: string;
    productWeight: number;
}

interface CODData {
    codAmount: number;
}



interface FormData {
    pickupData: PickupData;
    recipientData: Address;
    productData: ProductData;
    codData: CODData;
}


interface ResponseData {
    message: string;
    data: FormData;
}

export default function Dashboard() {
    const [pickupData, setPickupData] = useState<PickupData>({
        sender: '',
        pickupLocation: 'Nhận tại nhà',
        pickupDate: '',
        postOfficeId: '',
        pickupDay: '',
        timePeriod: '',
        isPostOfficePickup: false,
    });
    const [recipientData, setRecipientData] = useState<Address | null>(null);
    const [productData, setProductData] = useState<ProductData>({ productName: '', productWeight: 0 });
    const [codData, setCodData] = useState<CODData>({ codAmount: 0 });
    const [isChecked, setIsChecked] = useState<boolean>(false);

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

    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if (!isChecked) {
            alert('Vui lòng đồng ý với Điều khoản quy định');
            return;
        }

        alert(JSON.stringify({
            pickupData,
            recipientData,
            productData,
            codData,
        }))
        
        // if (!recipientData) {
        //     alert('Vui lòng điền đầy đủ thông tin người nhận');
        //     return;
        // }
        // const formData: FormData = {
        //     pickupData,
        //     recipientData,
        //     productData,
        //     codData,
        // };
        // alert("Form Data Submitted:", formData);
        // mutation.mutate(formData);
    }



    const handlePickupDataChange = (data: PickupData) => {
        console.log(data)
        setPickupData(data);
    };

    const handleAddressChange = (address: Address) => {
        console.log(address)
        setRecipientData((prevData) => ({
            ...prevData,
            ...address,
        }));
    };

    const handleProductChange = (product: Product) => {
        console.log(product)
        setProductData((prevData) => ({
            ...prevData,
            ...product,
        }))
    }

    const handleCheckboxChange = (checked: boolean) => {
        setIsChecked(checked);
    };


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
                                <PickupForm onPickupDataChange={handlePickupDataChange} />
                                <RecipientForm onRecipientDataChange={handleAddressChange} />
                            </div>
                            <div className="p-6">
                                <ProductForm onSubmit={handleProductChange} />
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
                                            checked={isChecked}
                                            onCheckedChange={handleCheckboxChange}
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