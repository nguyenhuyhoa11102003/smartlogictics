import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import AddressForm from "./AddressForm";
import { RecipientData } from "@/modules/order/models/RecipientData";
import React, { FormEvent, useEffect, useState } from "react";
import { Address } from "@/modules/address/models/AddressModel";
import { Button } from "@/components/ui/button";
import axios from "axios";
import { sendReceiverData } from "@/modules/customer/services/ReceiverService";
import { CreateReceiverRequest } from "../models/CreateReceiverRequest";


interface RecipientFormProps {
    onRecipientDataChange: (data: Address) => void;
}
export default function RecipientForm({ onRecipientDataChange }: RecipientFormProps) {
    const [recipientData, setRecipientData] = useState<Address>({
        id: "",
        contactName: "",
        phone: "",
        addressDetail: "",
        districtId: 0,
        stateOrProvinceId: 0,
        countryId: 0,
        city: "",
        zipCode: "",
        districtName: "",
        stateOrProvinceName: "",
        countryName: "",
        isActive: true,
        wardId: 0,
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

    const handleChange1 = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target;
        setEmail(value);
    }

    const handleAddressChange = (update: Address) => {
        setRecipientData((prevData) => ({
            ...prevData,
            ...update,
        }));
    };



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

        if (!recipientData.addressDetail) {
            newErrors.addressDetail = 'Địa chỉ dòng 1 là bắt buộc';
        }

        if (!recipientData.zipCode) {
            newErrors.zipCode = 'Mã bưu điện là bắt buộc';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // useEffect(() => {
    //     // if (validate()) {
    //     //     onRecipientDataChange(recipientData);
    //     // }
    //     // else {
    //     //     console.log('error validate recipient form')
    //     // }
    //     onRecipientDataChange(recipientData);
    // }, [recipientData]);


    const [history, setHistory] = useState([]);
    useEffect(() => {
        setHistory([]);
    }, []);
    const [selectedOption, setSelectedOption] = useState<string>("new");
    const [email, setEmail] = useState<string>("");
    const handleSaveRecipient = async (e: FormEvent) => {
        e.preventDefault();

        console.log(recipientData);
        // console.log(email); 

        const payload = {
            "fullName": recipientData.contactName,
            "phoneNumber": recipientData.phone,
            "email": email,
            "province": recipientData.stateOrProvinceName,
            "district": recipientData.districtName,
            "ward": recipientData.wardName,
            "street": recipientData.addressDetail,
            "postalCode": recipientData.zipCode,
            "provinceCode": recipientData.stateOrProvinceId,
            "districtCode": recipientData.districtId,
            "communeCode": recipientData.wardId,
        }
        const isValid = Object.values(payload).every(value => value !== null && value !== undefined && value !== "");
        if (!isValid) {
            alert("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        const receiverHistorys = await sendReceiverData( payload as unknown as CreateReceiverRequest);

    };
    const handleSelectHistory = (index) => {
        if (index !== "") {
            setRecipientData(history[index]);
        }

    };

    return (
        <div className="border-2 shadow-lg p-4 mt-4">
            <h2 className="font-bold text-lg mb-4">Thông Tin Người Nhận</h2>

            {/* Chọn loại nhập thông tin */}
            <div className="mb-4">
                <label className="mr-4">
                    <input
                        type="radio"
                        value="existing"
                        checked={selectedOption === "existing"}
                        onChange={() => setSelectedOption("existing")}
                    />
                    Chọn người nhận trước đó
                </label>
                <label>
                    <input
                        type="radio"
                        value="new"
                        checked={selectedOption === "new"}
                        onChange={() => {
                            setSelectedOption("new");
                        }}
                    />
                    Tạo địa chỉ mới
                </label>
            </div>



            <form onSubmit={handleSaveRecipient}>
                {/* Select Previous Recipient */}
                {selectedOption === "existing" && history.length == 0 && (
                    <div className="mb-4">
                        <Label htmlFor="historySelect">Chọn người nhận trước đó</Label>
                        <select
                            id="historySelect"
                            onChange={(e) => handleSelectHistory(e.target.value)}
                            className="w-full border p-2"
                        >
                            <option value="">-- Chọn người nhận --</option>
                            {history.map((item, index) => (
                                <option key={index} value={index}>
                                </option>
                            ))}
                        </select>
                    </div>
                )}


                {selectedOption === "new" ? (
                    <>
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

                        <div className="mb-4">
                            <Label htmlFor="contactName">Email</Label>
                            <Input
                                id="email"
                                type="text"
                                placeholder="Nhập email"
                                value={email}
                                onChange={handleChange1}
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

                        <AddressForm onAddressChange={handleAddressChange} />

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
                        <Button type="submit" className="bg-blue-500 text-white">Gửi ngay</Button>
                    </>
                ) : null}
            </form>
        </div>
    );
}
