import {
    Tabs,
    TabsContent,
    TabsList,
    TabsTrigger,
} from "@/components/ui/tabs"

import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { useState } from "react";
import { useMutation, UseMutationResult } from '@tanstack/react-query';
import { PickupData } from "@/modules/order/models/PickUpData";
import { Address } from "@/modules/address/models/AddressModel";
import { Product } from "@/modules/catalog/models/Product";
import PickupForm from "@/modules/order/components/PickupForm";
import RecipientForm from "@/modules/order/components/RecipientForm";
import ProductForm from "@/modules/order/components/ProductForm";
import CODForm from "@/modules/order/components/CODFrom";
import { Order } from "@/modules/order/models/Order";
import { OrderStatus } from "@/modules/order/models/EOrderStatus";
import { EDeliveryMethod } from "@/modules/order/models/EDeliveryMethod";
import { EDeliveryServiceType } from "@/modules/order/models/EDeliveryServiceType";
import { CreateReceiverRequest } from "@/modules/order/models/CreateReceiverRequest";
import { sendReceiverData } from "@/modules/order/services/ReceiverService";
import { ReceiverInfResponse } from "@/modules/order/models/ReceiverInfResponse";
interface CODData {
    codAmount: number;
}

interface ResponseData {
    message: string;
    data: FormData;
}
export default function OrderForm() {
    const [pickupData, setPickupData] = useState<PickupData>({
        sender: '3d04b569-2c6a-41f8-afc5-d443e94ba647',
        pickupLocation: 'Nhận tại nhà',
        pickupDate: '',
        postOfficeId: '',
        pickupDay: '',
        timePeriod: '',
        isPostOfficePickup: false,
    });
    const [recipientData, setRecipientData] = useState<Address | null>(null);
    const [productData, setProductData] = useState<Product | null>(null);
    const [codData, setCodData] = useState<CODData>({ codAmount: 0 });
    const [isChecked, setIsChecked] = useState<boolean>(false);

    // Mutation để gửi dữ liệu đến API
    const mutation = useMutation<ResponseData, Error, Order>({
        mutationFn: add
    });

    async function add(data: Order): Promise<ResponseData> {
        console.log('CreateOrderRequest :', data)
        const response = await fetch('http://localhost:8086/orders/orders/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Failed to submit form');
        }
        const result = await response.json();
        return result.result;
    }

    const handleSubmit = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        if (!isChecked) {
            alert('Vui lòng đồng ý với Điều khoản quy định');
            return;
        }

        const receiverData: CreateReceiverRequest = {
            customerId: "3d04b569-2c6a-41f8-afc5-d443e94ba647", // Id của khách hàng.
            fullName: recipientData?.contactName || "", // Tên đầy đủ của người nhận.
            phoneNumber: "0906919299", // Số điện thoại của người nhận.
            email: "nguyenhuyhoa2003@gmail.com", // Email liên hệ của người nhận.
            province: recipientData?.stateOrProvinceName ?? "",// Tỉnh.
            district: recipientData?.districtName ?? "", // Huyện.
            ward: recipientData?.wardName ?? "", // Xã/Phường.
            street: recipientData?.addressDetail ?? "", // Đường.
            postalCode: recipientData?.zipCode ?? "",// Mã bưu chính.
        }

        // const receiver: ReceiverInfResponse = await sendReceiverData(receiverData)

        const orderData: Order = {
            orderCreationStatus: OrderStatus.RECEIVED,  // Trạng thái có thể thay đổi tùy vào logic
            type: "string",  // Thêm giá trị cụ thể cho type
            customerCode: "KH0001",  // Cung cấp mã khách hàng
            contractCode: "string",  // Cung cấp mã hợp đồng
            informationOrder: {
                senderId: "3d04b569-2c6a-41f8-afc5-d443e94ba647",  // Sử dụng sender từ pickupData
                recipientId: "91dc33c9-9c08-4dc4-920f-a493a3b09c7f",  // Dùng id từ recipientData (hoặc để trống nếu không có)
                serviceCode: EDeliveryServiceType.ECONOMY,   // Giả sử là dịch vụ ECONOMY
                addonService: [],  // Bạn có thể thêm dịch vụ bổ sung nếu có
                additionRequest: [],  // Tương tự như addonService
                branchCode: pickupData.postOfficeId ?? "",  // Mã chi nhánh, có thể lấy từ state nếu có
                vehicle: "car",  // Phương tiện, có thể lấy từ state
                receivingMethod: pickupData.isPostOfficePickup ? "POST_OFFICE" : "CUSTOMER_ADDRESS",  // Phương thức nhận
                deliveryTime: pickupData.pickupDate,  // Thời gian giao hàng
                deliveryRequire: "",  // Cung cấp yêu cầu giao hàng nếu có
                deliveryInstruction: "",  // Cung cấp hướng dẫn giao hàng
                saleOrderCode: "",  // Mã đơn hàng bán, bạn có thể cung cấp nếu có
                contentNote: "",  // Ghi chú nội dung
                weight: productData?.totalWeight.toString() ?? "",  // Cân nặng của sản phẩm
                width: productData?.dimensions.width.toString() ?? "", // Chiều rộng có thể cung cấp nếu có
                length: productData?.dimensions.length.toString() ?? "",  // Chiều dài có thể cung cấp nếu có
                height: productData?.dimensions.height.toString() ?? "",  // Chiều cao có thể cung cấp nếu có
                broken: false,  // Xác định tình trạng sản phẩm (có thể là true hoặc false tùy trường hợp)
            }
        };
        // console.log(orderData)
        mutation.mutate(orderData);
    }



    const handlePickupDataChange = (data: PickupData) => {
        setPickupData(data);
    };

    const handleAddressChange = (address: Address) => {
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
    )

}
