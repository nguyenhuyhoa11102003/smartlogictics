import { useState, useEffect, Fragment } from 'react';
import { Label } from "@/components/ui/label";
import { Checkbox } from '@/components/ui/checkbox';
import { PickupData } from '@/modules/order/models/PickUpData';
import { Warehouse } from '@/modules/warehouse/models/Warehouse';
// import { getAllWarehouses } from '@/modules/warehouse/services/WarehouseService';
import { Button } from 'react-bootstrap';
import { useAuth } from '@/context/app.context';
import { AddressListProps } from '@/modules/address/components/AddressList';
import axios from 'axios';


interface PickupFormProps {
    onPickupDataChange: (data: PickupData) => void;
}

export default function PickupForm({ onPickupDataChange }: PickupFormProps) {
    const { accessToken, setAccessToken, clearAccessToken } = useAuth();
    const [pickupData, setPickupData] = useState<PickupData>({
        sender : {},
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
    // const [selectedSender, setSelectedSender] = useState<string>("");
    const [selectedSender, setSelectedSender] = useState(null);

    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);

    
    const [addresses, setAddresses] = useState<AddressListProps[]>([]);

    const fetchAddresses = async () => {
        const response = await axios.get(`http://localhost:8082/users/api/sender`, {});
        // console.info(`response:${JSON.stringify(response.data)}`);
        if (response.status === 200) {
          const senders = response.data._embedded.sender;
        //   console.info(`senders:${JSON.stringify(senders)}`);
    
          const updatedAddresses = [];
          for (const sender of senders) {
            // console.log('fullname:', sender.fullName);
            // console.log('phone:', sender.phoneNumber);
            // console.log('email:', sender.email);
            // console.log('address:', sender._links.address.href);
            const addressResponse = await axios.get(sender._links.address.href);
            if (addressResponse.status === 200) {
            //   console.log('address details:', addressResponse.data);
              updatedAddresses.push({
                senderName: sender.fullName,
                senderPhone: sender.phoneNumber,
                senderMail: sender.email,
                senderAddress: addressResponse.data.street,
                senderProvinceCode: addressResponse.data.provinceCode,
                senderProvinceName: addressResponse.data.province,
                senderDistrictCode: addressResponse.data.districtCode,
                senderDistrictName: addressResponse.data.district,
                senderCommuneCode: addressResponse.data.wardCode,
                senderCommuneName: addressResponse.data.ward,
                senderPostalCode: addressResponse.data.postalCode,
                senderId: sender.id
              });
            } else {
              console.error('Failed to fetch address:', addressResponse.status);
            }};
            // console.info(`updatedAddresses:${JSON.stringify(updatedAddresses)}`);
            setAddresses(updatedAddresses);
        }
      }
      useEffect(() => {
        fetchAddresses();
      }, []);

    useEffect(() => {
        // const fetchWarehouses = async () => {
        //     try {
        //         const response = await getAllWarehouses();
        //         setWarehouses(response)
        //     }
        //     catch (e) {
        //         alert('error')
        //     }
        //     getAllWarehouses()
        //         .then((data) => setWarehouses(data))
        //         .catch((error) => {
        //             setWarehouses([])
        //             // console.error('Error fetching warehouses:', error);
        //         })
        // };
        // fetchWarehouses();
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
        // const pickupLocation = showPostOffices
        //     ? warehouses.find((p) => p.id.toString() === selectedPostOffice)?.name || 'Chưa chọn bưu cục'
        //     : 'Nhận tại nhà';

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
            sender: {},
            // pickupLocation: pickupLocation,
            pickupLocation : "", 
            pickupDate: pickupDate,
            postOfficeId: selectedPostOffice,
            pickupDay: pickupDay,
            timePeriod: timePeriod,
            isPostOfficePickup: showPostOffices,
        };

        setPickupData(updatedData);
        // onPickupDataChange(updatedData);
    }, [showPostOffices, selectedSender, pickupDay, timePeriod, selectedPostOffice]);

    
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        alert('Xac nhan thong tin nguoi gui thanh cong');
        onPickupDataChange(pickupData)
    }

    const handleSenderChange = (e : any) => {
        const senderId = e.target.value
        const senderInfo = addresses.find(acount => acount.senderId === senderId);
        
        if (senderInfo) {
            setPickupData(prevData => ({
                ...prevData,
                sender: senderInfo, 
            }));
        }
    
    };
    

    


    return (
        <div className="border-2 shadow-lg p-4">
            <div className="flex justify-between space-x-2">
                <h2 className="font-bold text-lg mb-4">NGƯỜI GỬI</h2>
                {/* <div className="flex items-center">
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
                    <Button variant="outline" onClick={() => {}}>
                        + Thêm địa chỉ mới
                    </Button>
                </div> */}
            </div>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <Label htmlFor="senderName">Người gửi</Label>
                    <select
                        id="senderName"
                        className="w-full border rounded p-2"
                        value={selectedSender || ""}
                        onChange={handleSenderChange}
                    >
                        {/* <option value="">Chọn người gửi...</option> */}
                        {addresses.map((acount) => (
                            <option key={acount.senderId} value={acount.senderId}>{acount.senderName + "," + acount.senderAddress} </option>
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

                <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                    Xác nhận thông tin người gửi
                </button>

                {/* {showPostOffices && (<div className="mb-4">
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
                </div>)} */}
            </form>
            
        </div>
    );
}
