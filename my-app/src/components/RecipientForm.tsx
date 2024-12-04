import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import AddressForm from "./AddressForm";
import { RecipientData } from "@/modules/order/models/RecipientData";
import React, { useEffect, useState } from "react";
import { Address } from "@/modules/address/models/AddressModel";


interface RecipientFormProps {
    onRecipientDataChange: (data: Address) => void;
}
export default function RecipientForm({ onRecipientDataChange }: RecipientFormProps) {
    const [recipientData, setRecipientData] = useState<Address>({
        contactName: "", // Default empty value for required fields
        phone: "",
        addressLine1: "",
        districtId: 0, // Default 0 for numeric fields
        stateOrProvinceId: 0,
        countryId: 0,
        addressLine2: "",
        city: "",
        zipCode: "",
        districtName: "",
        stateOrProvinceName: "",
        countryName: "",
        isActive: true, // Default true or false depending on your needs
    });

    const [errors, setErrors] = useState<{ [key in keyof RecipientData]?: string }>({});


    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = event.target;
        setRecipientData((prevData: Address) => {
            return {
                ...prevData,
                [id]: value,
            };
        });
    }

    const validate = () => {
        const newErrors: { [key in keyof Address]?: string } = {};

        if (!recipientData.contactName) {
            newErrors.contactName = 'Họ và tên là bắt buộc';
        }

        if (!recipientData.phone) {
            newErrors.phone = 'Số điện thoại là bắt buộc';
        } else if (!/^\d{10,11}$/.test(recipientData.phone)) {
            newErrors.phone = 'Số điện thoại không hợp lệ';
        }

        if (!recipientData.addressLine1) {
            newErrors.addressLine1 = 'Địa chỉ dòng 1 là bắt buộc';
        }

        if (!recipientData.zipCode) {
            newErrors.zipCode = 'Mã bưu điện là bắt buộc';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    useEffect(() => {
        if (validate()) {
            onRecipientDataChange(recipientData);
        }
    }, [recipientData]);

    return (
        <div className="border-2 shadow-lg p-4 mt-4">
            <h2 className="font-bold text-lg mb-4">Thông Tin Người Nhận</h2>
            <form>
                {/* Contact Name */}
                <div className="mb-4">
                    <Label htmlFor="contactName">Họ và tên</Label>
                    <Input
                        id="contactName"
                        type="text"
                        placeholder="Nhập họ và tên"
                        value={recipientData.contactName}
                        onChange={handleChange}
                        className="w-full"
                    />
                    {errors.contactName && <p className="text-red-500 text-sm">{errors.contactName}</p>}
                </div>

                {/* Phone */}
                <div className="mb-4">
                    <Label htmlFor="phone">Số điện thoại</Label>
                    <Input
                        id="phone"
                        type="tel"
                        placeholder="Nhập số điện thoại"
                        className="w-full"
                        value={recipientData.phone}
                        onChange={handleChange}
                    />
                    {errors.phone && <p className="text-red-500 text-sm">{errors.phone}</p>}
                </div>

                {/* Address Line 1 */}
                <div className="mb-4">
                    <Label htmlFor="addressLine1">Địa chỉ dòng 1</Label>
                    <Input
                        id="addressLine1"
                        type="text"
                        placeholder="Nhập địa chỉ dòng 1"
                        className="w-full"
                        value={recipientData.addressLine1}
                        onChange={handleChange}
                    />
                    {errors.addressLine1 && <p className="text-red-500 text-sm">{errors.addressLine1}</p>}
                </div>

                <AddressForm onAddressChange={() => { }} />

                {/* Zip Code */}
                <div className="mb-4">
                    <Label htmlFor="zipCode">Mã bưu điện</Label>
                    <Input
                        id="zipCode"
                        type="text"
                        placeholder="Nhập mã bưu điện"
                        value={recipientData.zipCode}
                        onChange={handleChange}
                        className="w-full"
                    />
                </div>
                {errors.zipCode && <p className="text-red-500 text-sm">{errors.zipCode}</p>}
            </form>
        </div>
    );
}
