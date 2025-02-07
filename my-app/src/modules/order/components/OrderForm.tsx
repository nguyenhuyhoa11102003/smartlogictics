import {
    Tabs,
    TabsContent,
    TabsList,
    TabsTrigger,
} from "@/components/ui/tabs"

import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { useEffect, useState } from "react";
import { useMutation } from '@tanstack/react-query';
import { PickupData } from "@/modules/order/models/PickUpData";
import { Product } from "@/modules/catalog/models/Product";
import PickupForm from "@/modules/order/components/PickupForm";
import RecipientForm from "@/modules/order/components/RecipientForm";
import ProductForm from "@/modules/order/components/ProductForm";
import CODForm from "@/modules/order/components/CODFrom";
import { Order } from "@/modules/order/models/Order";
import { useAuth } from "@/context/app.context";
import http from "@/utils/http";
interface CODData {
    codAmount: number;
}

interface ResponseData {
    message: string;
    data: FormData;
}

export type ReceiverProps = {
    id?: string;
    contactName: string;
    phone: string;
    addressDetail: string;
    city?: string;
    zipCode?: string;
    districtId: number;
    districtName?: string;
    wardId: number;
    wardName?: string;
    stateOrProvinceId: number;
    stateOrProvinceName?: string;
    countryId: number;
    countryName?: string;
    isActive?: boolean;

};



interface ReceiverProps {
    recipientId : string;
    recipientName: string;
    receiverPhone: string;
    receiverAddress: string;
    receiverProvinceCode: string;
    receiverProvinceName: string;
    receiverDistrictCode: string;
    receiverDistrictName: string;
    receiverWard: string;
    receiverWardCode: string;
    receiverStreet: string;
    receiverPostalCode: string;
    receiverEmail: string;
}

export default function OrderForm() {
    const { accessToken, setAccessToken, clearAccessToken } = useAuth();
    const [pickupData, setPickupData] = useState<PickupData>({
        sender: '3d04b569-2c6a-41f8-afc5-d443e94ba647',
        pickupLocation: 'Nhận tại nhà',
        pickupDate: '',
        postOfficeId: '',
        pickupDay: '',
        timePeriod: '',
        isPostOfficePickup: false,
    });
    const [recipientData, setRecipientData] = useState<ReceiverProps | null>(null);

    const [productData, setProductData] = useState<Product | null>(null);
    const [codData, setCodData] = useState<CODData>({ codAmount: 0 });
    const [isChecked, setIsChecked] = useState<boolean>(false);

    // Mutation để gửi dữ liệu đến API
    const mutation = useMutation<ResponseData, Error, Order>({
        mutationFn: add
    });

    async function add(data: any): Promise<ResponseData> {
        console.log(JSON.stringify(data))
        const response = await http.post('http://localhost:8086/order/orders/create', data, {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json',

            },
        });
        console.log(response)

    }

    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if (!isChecked) {
            alert('Vui lòng đồng ý với Điều khoản quy định');
            return;
        }

        // console.log(JSON.stringify(pickupData))
        // console.log(JSON.stringify(recipientData))
        // console.log(JSON.stringify(paymentInfo))

        const payload = {
            orderCreationStatus: "RECEIVED",
            customerCode: "string",
            informationOrder: {
                senderId: pickupData?.data?.sender?.senderId,
                senderName: pickupData?.data?.sender?.senderName,

                receiverId: recipientData?.recipientId,
                recipientName: recipientData?.recipientName,
                receiverPhone: recipientData?.receiverPhone,
                receiverAddress: recipientData?.receiverAddress,
                receiverProvinceCode: recipientData?.receiverProvinceCode,
                receiverProvinceName: recipientData?.receiverProvinceName,
                receiverDistrictCode: recipientData?.receiverDistrictCode,
                receiverDistrictName: recipientData?.receiverDistrictName,
                receiverWard: recipientData?.receiverWard,
                receiverWardCode: recipientData?.receiverWardCode,
                receiverStreet: recipientData?.receiverStreet,
                receiverPostalCode: recipientData?.receiverPostalCode,
                receiverEmail: recipientData?.receiverEmail,

                codAmount: 0,
                shippingMethod: "ROAD",
                addOnServices: ["AIR_CARGO"],
                deliveryTime: "string",

                branchCode: "string",
                serviceCode: "ECONOMY",
                shippingZone: "NOI_TINH",
                vehicle: "string",
                receivingMethod: "CUSTOMER_ADDRESS",

                deliveryRequire: paymentInfo?.deliveryRequire,
                deliveryInstruction: paymentInfo?.deliveryInstruction,
                moreRequire: paymentInfo?.moreRequire,
                contentNote: paymentInfo?.contentNote,

                weight: 1,
                width: 0,
                length: 0,
                height: 0,

                shipmentId: "string",
                saleOrderCode: "string",
                paymentType: "PREPAID",
                broken: true,

            }
        };
        mutation.mutate(payload);
    }



    const handlePickupDataChange = (data: any) => {
        setPickupData((prev) => ({
            ...prev,
            data
        }))

    };

    const handleAddressChange = (address: ReceiverProps) => {
        setRecipientData((prevData) => ({
            ...prevData,
            ...address,
        }));
    };

    const handleProductChange = (product: Product) => {
        setProductData((prevData) => ({
            ...prevData,
            ...product,
        }))
    }

    const handleCheckboxChange = (checked: boolean) => {
        setIsChecked(checked);
    };


    const [paymentInfo, setPaymentInfo] = useState(null);

    const handlePaymentConfirm = (data: any) => {
        setPaymentInfo(data);
    };

    return (
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
                        <CODForm onConfirm={handlePaymentConfirm} />
                    </div>
                </div>
                <div className="flex justify-normal p-6">
                    <div className="mb-4 flex items-center">
                        <input
                            type="checkbox"
                            checked={isChecked}
                            onChange={(e) => setIsChecked(e.target.checked)}
                        />
                        <label className="ml-2">Tôi đồng ý với Điều khoản quy định</label>
                    </div>
                    <button
                        onClick={handleSubmit}
                        className="w-full  text-white p-2 rounded-lg bg-blue-600 transition"
                    >
                        Tạo đơn hàng
                    </button>
                </div>
            </TabsContent>
        </Tabs>
    )

}
