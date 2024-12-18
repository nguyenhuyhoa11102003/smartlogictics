import { useState, useEffect } from 'react';
import { Label } from "@/components/ui/label";
import { Checkbox } from '@/components/ui/checkbox';
import { PickupData } from '@/modules/order/models/PickUpData';
import { Warehouse } from '@/modules/warehouse/models/Warehouse';
import { getAllWarehouses } from '@/modules/warehouse/services/WarehouseService';
import { error } from 'console';

interface PickupFormProps {
    onPickupDataChange: (data: PickupData) => void;
}

export default function PickupForm({ onPickupDataChange }: PickupFormProps) {
    const [pickupData, setPickupData] = useState<PickupData>({
        sender: '',
        pickupLocation: 'Nhận tại nhà',
        pickupDate: '',
        postOfficeId: '',
        pickupDay: '',
        timePeriod: '',
        isPostOfficePickup: false,
    });

    const [pickupDay, setPickupDay] = useState<string>('');
    const [timePeriod, setTimePeriod] = useState<string>('');
    const [currentDate, setCurrentDate] = useState<string>('');
    const [showPostOffices, setShowPostOffices] = useState<boolean>(false);
    const [selectedPostOffice, setSelectedPostOffice] = useState<string>("");
    const [selectedSender, setSelectedSender] = useState<string>('3d04b569-2c6a-41f8-afc5-d443e94ba647');
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);


    const accounts = [
        {
            id: 1,
            addressLine: "266/58, Tôn Đản, Quận 1",
            city: "Hồ Chí Minh",
            country: "Việt Nam",
        },
        {
            id: 2,
            addressLine: "123 Đường ABC, Quận 2",
            city: "Hồ Chí Minh",
            country: "Việt Nam",
        }
    ];


    useEffect(() => {
        const fetchWarehouses = async () => {
            try {
                const response = await getAllWarehouses();
                setWarehouses(response)
            }
            catch (e) {
                alert('error')
            }
            // getAllWarehouses()
            //     .then((data) => setWarehouses(data))
            //     .catch((error) => {
            //         // setWarehouses([])
            //         // console.error('Error fetching warehouses:', error);
            //     })
        };
        fetchWarehouses();
    }, []);


    useEffect(() => {
        // Get today's date in format DD/MM/YYYY
        const today = new Date();
        const day = today.getDate();
        const month = today.getMonth() + 1; // months are 0-indexed
        const year = today.getFullYear();
        const formattedDate = `${day < 10 ? '0' + day : day}/${month < 10 ? '0' + month : month}/${year}`;
        setCurrentDate(formattedDate); // Set the current date in state
    }, []);

    // Helper function to format dates
    const getFormattedDate = (daysOffset: number) => {
        const date = new Date();
        date.setDate(date.getDate() + daysOffset);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        return `${day < 10 ? '0' + day : day}/${month < 10 ? '0' + month : month}/${year}`;
    };

    useEffect(() => {
        const pickupLocation = showPostOffices
            ? warehouses.find((p) => p.id.toString() === selectedPostOffice)?.name || 'Chưa chọn bưu cục'
            : 'Nhận tại nhà';

        const pickupDate = pickupDay && timePeriod
            ? `${pickupDay} - ${(() => {
                switch (timePeriod) {
                    case 'morning':
                        return '(07:30 - 12:00)';
                    case 'afternoon':
                        return '(13:30 - 18:00)';
                    case 'evening':
                        return '(18:30 - 21:00)';
                    default:
                        return 'Chưa chọn thời gian';
                }
            })()}`
            : 'Chưa chọn thời gian';

        const updatedData: PickupData = {
            sender: selectedSender,
            pickupLocation: pickupLocation,
            pickupDate: pickupDate,
            postOfficeId: selectedPostOffice,
            pickupDay: pickupDay,
            timePeriod: timePeriod,
            isPostOfficePickup: showPostOffices,
        };
        // console.log(updatedData)
        setPickupData(updatedData);
        onPickupDataChange(updatedData);
    }, [showPostOffices, selectedSender, pickupDay, timePeriod, selectedPostOffice]);


    return (
        <div className="border-2 shadow-lg p-4">
            <div className="flex justify-between space-x-2">
                <h2 className="font-bold text-lg mb-4">NGƯỜI GỬI</h2>
                <div className="flex items-center">
                    <Checkbox
                        id="terms"
                        checked={showPostOffices}
                        onCheckedChange={(checked: boolean) => setShowPostOffices(checked)}
                    />
                    <label
                        htmlFor="terms"
                        className="text-sm font-medium leading-none ml-2"
                    >
                        Gửi tại bưu cục
                    </label>
                </div>
            </div>
            <form>
                <div className="mb-4">
                    <Label htmlFor="senderName">Người gửi</Label>
                    <select
                        id="senderName"
                        className="w-full border rounded p-2"
                        value={selectedSender}
                        onChange={
                            (e) => setSelectedSender(e.target.value)
                        }
                    >
                        <option value="">Chọn người gửi...</option>
                        {accounts.map((acount) => (
                            <option key={acount.id} value={acount.id}>{acount.addressLine + "," + acount.city} </option>
                        ))}
                    </select>
                </div>

                {!showPostOffices && (<>
                    <div className="mb-4">
                        <label htmlFor="pickupDay" className="block font-bold mb-2">
                            Chọn ngày nhận
                        </label>
                        <select
                            id="pickupDay"
                            className="w-full border rounded p-2 mb-4"
                            value={pickupDay}
                            onChange={(e) => setPickupDay(e.target.value)}
                        >
                            <option value="">Chọn ngày...</option>
                            <option value={currentDate}>{`Hôm nay (${currentDate})`}</option>
                            <option value={getFormattedDate(1)}>{`Ngày mai (${getFormattedDate(1)})`}</option>
                            <option value={getFormattedDate(2)}>{`Ngày kia (${getFormattedDate(2)})`}</option>
                        </select>
                    </div>

                    {/* Select for time period */}
                    <div className="mb-4">
                        <label htmlFor="timePeriod" className="block font-bold mb-2">
                            Chọn thời gian
                        </label>
                        <select
                            id="timePeriod"
                            className="w-full border rounded p-2"
                            value={timePeriod}
                            onChange={(e) => setTimePeriod(e.target.value)}
                            disabled={!pickupDay}
                        >
                            <option value="">Chọn thời gian...</option>
                            <option value="morning">{`Sáng (07:30 - 12:00)`}</option>
                            <option value="afternoon">{`Chiều (13:30 - 18:00)`}</option>
                            <option value="evening">{`Tối (18:30 - 21:00)`}</option>
                        </select>
                    </div>
                </>)}

                {showPostOffices && (<div className="mb-4">
                    <label htmlFor="postOffice" className="block font-bold mb-2">
                        Chọn bưu cục
                    </label>
                    <select
                        id="postOffice"
                        className="w-full border rounded p-2"
                        value={selectedPostOffice}
                        onChange={(e) => setSelectedPostOffice(e.target.value)}
                    >
                        <option value="">Chọn bưu cục...</option>
                        {warehouses.map((postOffice) => (
                            <option key={postOffice.id} value={postOffice.id}>
                                {postOffice.name} - {postOffice.addressDetail}
                            </option>
                        ))}
                    </select>
                </div>)}
            </form>
        </div>
    );
}
