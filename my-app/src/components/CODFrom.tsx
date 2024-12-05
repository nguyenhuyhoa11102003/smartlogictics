import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Label, } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Checkbox } from './ui/checkbox';
import { Separator } from "@/components/ui/separator"
import { Textarea } from "@/components/ui/textarea"

const CODForm = () => {
    const [amount, setAmount] = useState<number>(0);
    const [notes, setNotes] = useState<string>("");
    const [payer, setPayer] = useState<"sender" | "receiver">("sender");
    const [saveInfo, setSaveInfo] = useState<boolean>(false);

    return (
        <div className="p-6 rounded-lg shadow-lg p-4 mt-4 rounded-lg">
            <h3 className="text-lg font-semibold mb-4">TIỀN THU HỘ</h3>
            <div className="mb-4">
                <Label className="block">Hình thức thanh toán tiền COD</Label>
                <p className="text-sm text-gray-600">Thanh toán khi nhận hàng</p>
            </div>

            <div className="mb-4 flex items-center">
                <Checkbox
                    checked={saveInfo}
                    onCheckedChange={(checked: boolean) => setSaveInfo(checked)}
                />
                <Label className="ml-2">Lưu thông tin đơn hàng</Label>
            </div>

            <div className="mb-4">
                <Label htmlFor="amount" className="block">Thu hộ bằng tiền hàng</Label>
                <Input
                    id="amount"
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(Number(e.target.value))}
                    className="w-full"
                />
            </div>

            <div className="mb-4">
                <Label className="block">Ghi chú</Label>
                <Textarea
                    value={notes}
                    onChange={(e) => setNotes(e.target.value)}
                    placeholder="Nhập ghi chú"
                    className="w-full"
                />
            </div>

            {/* <div className="mb-4">
                <Label className="block">Người trả cước</Label>
                <RadioGroup
                    value={payer}
                    onChange={(value) => setPayer(value as "sender" | "receiver")}
                >
                    <div className="flex items-center mb-2">
                        <Radio value="sender" />
                        <Label className="ml-2">Người gửi</Label>
                    </div>
                    <div className="flex items-center">
                        <Radio value="receiver" />
                        <Label className="ml-2">Người nhận</Label>
                    </div>
                </RadioGroup>
            </div> */}
        </div>
    );
};

export default CODForm;
