import { useState, useEffect } from "react";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Address } from "@/modules/address/models/AddressModel";

interface Province {
    idProvince: string;
    name: string;
}

interface District {
    idDistrict: string;
    name: string;
}

interface Ward {
    idCommune: string;
    name: string;
}

interface AddressFormProps {
    onAddressChange: (data: Address) => void;
}

export default function AddressForm({  onAddressChange }: AddressFormProps) {
    const [provinces, setProvinces] = useState<Province[]>([]);
    const [districts, setDistricts] = useState<District[]>([]);
    const [wards, setWards] = useState<Ward[]>([]);

    const [selectedProvince, setSelectedProvince] = useState<Province | null>(null);
    const [selectedDistrict, setSelectedDistrict] = useState<District | null>(null);
    const [selectedWard, setSelectedWard] = useState<Ward | null>(null);
    const [street, setStreet] = useState<string>("");

    useEffect(() => {
        // Lấy danh sách tỉnh/thành phố
        async function fetchProvinces() {
            const res = await fetch("https://vietnam-administrative-division-json-server-swart.vercel.app/province");
            const data: Province[] = await res.json();
            setProvinces(data);
        }
        fetchProvinces();
    }, []);

    useEffect(() => {
        if (selectedProvince !== null) {
            // Lấy danh sách quận/huyện khi chọn tỉnh
            async function fetchDistricts() {
                const res = await fetch(
                    `https://vietnam-administrative-division-json-server-swart.vercel.app/district/?idProvince=${selectedProvince?.idProvince}`
                );
                const data: District[] = await res.json();
                setDistricts(data);
            }
            fetchDistricts();

        } else {
            setDistricts([]);
            setWards([]);
        }
    }, [selectedProvince]);

    useEffect(() => {
        if (selectedDistrict) {
            // Lấy danh sách xã/phường khi chọn quận/huyện
            async function fetchWards() {
                const res = await fetch(
                    `https://vietnam-administrative-division-json-server-swart.vercel.app/commune/?idDistrict=${selectedDistrict?.idDistrict}`
                );
                const data: Ward[] = await res.json();
                setWards(data);
            }
            fetchWards();
        } else {
            setWards([]);
        }
    }, [selectedDistrict]);

    useEffect(() => {
        if (selectedProvince && selectedDistrict && selectedWard && street) {
            onAddressChange({
                contactName: "", // Add or modify the contact name as needed
                phone: "", // Add or modify phone field
                addressLine1: street,
                addressLine2: "", // Can add logic for a second address line if needed
                city: selectedProvince.name,
                districtId: parseInt(selectedDistrict.idDistrict),
                districtName: selectedDistrict.name,
                stateOrProvinceId: parseInt(selectedProvince.idProvince),
                stateOrProvinceName: selectedProvince.name,
                countryId: 1, // Adjust country ID if needed
                countryName: "Vietnam", // Update if needed
                isActive: true, // Adjust based on your needs
            });
        }
    }, [selectedProvince, selectedDistrict, selectedWard, street, onAddressChange]);


    return (
        <div className="border-2 shadow-lg p-4 mt-4">
            <h2 className="font-bold text-lg mb-4">Địa chỉ</h2>
            {/* Nhập địa chỉ */}
            <div className="mb-4">
                <Input
                    type="text"
                    placeholder="Nhập địa chỉ (số nhà, tên đường)"
                    className="w-full border rounded px-3 py-2"
                />
            </div>

            <div className="flex gap-4 mb-4">
                {/* Dropdown Tỉnh/Thành phố */}
                <div className="w-1/2">
                    <Label className="block mb-2 font-medium">Tỉnh/Thành phố</Label>
                    <select
                        onChange={(e) => {
                            const selected = provinces.find(p => p.idProvince === e.target.value);
                            setSelectedProvince(selected || null)
                        }}
                        className="w-full border rounded px-3 py-2"
                    >
                        <option value="">Chọn Tỉnh/Thành phố</option>
                        {provinces.map((province) => (
                            <option key={province.idProvince} value={province.idProvince}>
                                {province.name}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Dropdown Huyện/Quận */}
                <div className="w-1/2">
                    <Label className="block mb-2 font-medium">Huyện/Quận</Label>
                    <select
                        onChange={(e) => {
                            const selected = districts.find(p => p.idDistrict === e.target.value);
                            setSelectedDistrict(selected || null)
                        }}
                        className="w-full border rounded px-3 py-2"
                        disabled={!selectedProvince}
                    >
                        <option value="">Chọn Huyện/Quận</option>
                        {districts.map((district) => (
                            <option key={district.idDistrict} value={district.idDistrict}>
                                {district.name}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="flex gap-4 mb-4">
                {/* Dropdown Xã/Phường */}
                <div className="w-1/2">
                    <Label className="block mb-2 font-medium">Xã/Phường</Label>
                    <select
                        onChange={(e) => {
                            const selected = wards.find(p => p.idCommune === e.target.value);
                            setSelectedWard(selected || null);
                        }}
                        className="w-full border rounded px-3 py-2"
                        disabled={!selectedDistrict}
                    >
                        <option value="">Chọn Xã/Phường</option>
                        {wards.map((ward) => (
                            <option key={ward.idCommune} value={ward.idCommune}>
                                {ward.name}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Nhập Đường/Thôn/Xóm */}
                <div className="w-1/2">
                    <Label className="block mb-2 font-medium">Đường/Thôn/Xóm</Label>
                    <input
                        type="text"
                        placeholder="Nhập Đường/Thôn/Xóm"
                        className="w-full border rounded px-3 py-2"
                        value={street}
                        onChange={(e) => setStreet(e.target.value)}
                    />
                </div>
            </div>
        </div>
    );
}
