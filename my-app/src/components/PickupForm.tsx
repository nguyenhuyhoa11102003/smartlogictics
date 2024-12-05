import { useState, useEffect } from 'react';
import { Label } from "@/components/ui/label";
import { Checkbox } from './ui/checkbox';
import { PickupData } from '@/modules/order/models/PickUpData';

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
    const [selectedPostOffice, setSelectedPostOffice] = useState<string>('');
    const [selectedSender, setSelectedSender] = useState<string>('');
    const postOffices = [
        { id: 'post1', name: 'Bưu cục A - 123 Đường ABC' },
        { id: 'post2', name: 'Bưu cục B - 456 Đường XYZ' },
        { id: 'post3', name: 'Bưu cục C - 789 Đường 123' },
    ];

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
            ? postOffices.find((p) => p.id === selectedPostOffice)?.name || 'Chưa chọn bưu cục'
            : 'Nhận tại nhà';

        const pickupDate = pickupDay && timePeriod
            ? `${pickupDay} - ${timePeriod}`
            : 'Chưa chọn thời gian';

        const updatedData: PickupData = {
            sender: selectedSender,
            pickupLocation,
            pickupDate,
            postOfficeId: selectedPostOffice,
            pickupDay,
            timePeriod,
            isPostOfficePickup: showPostOffices,
        };

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
                        onChange={(e) => setSelectedSender(e.target.value)}
                    >
                        <option value="">Chọn người gửi...</option>
                        <option value="Nguyễn Huy Hòa">Nguyễn Huy Hòa - 266/58, Tôn Đản...</option>
                        <option value="Nguyễn Văn A">Nguyễn Văn A - 123 Đường ABC...</option>
                        <option value="Trần Thị B">Trần Thị B - 456 Đường XYZ...</option>
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
                            <option value="today">{`Hôm nay (${currentDate})`}</option>
                            <option value="tomorrow">{`Ngày mai (${getFormattedDate(1)})`}</option>
                            <option value="dayAfterTomorrow">{`Ngày kia (${getFormattedDate(2)})`}</option>
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
                            disabled={!pickupDay} // Disable if no pickup day is selected
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
                        {postOffices.map((postOffice) => (
                            <option key={postOffice.id} value={postOffice.id}>
                                {postOffice.name}
                            </option>
                        ))}
                    </select>
                </div>)}
            </form>
        </div>
    );
}
