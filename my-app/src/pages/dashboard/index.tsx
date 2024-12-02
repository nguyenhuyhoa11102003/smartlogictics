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

export default function Dashboard() {
    return (
        <LayoutDashboard>
            <Tabs defaultValue="inside" className="w-full">
                <TabsList className="grid grid-cols-2">
                    <TabsTrigger value="inside">Tạo đơn trong nước</TabsTrigger>
                    <TabsTrigger value="outside">Tạo đơn quốc tế</TabsTrigger>
                </TabsList>
                <TabsContent value="inside">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-6">
                        <div className="p-6">
                            <div className="border-2 border-red-500 p-4">
                                <h2 className="font-bold text-lg mb-4">NGƯỜI GỬI</h2>
                                <form>
                                    <div className="mb-4">
                                        <Label htmlFor="senderName">Người gửi</Label>
                                        <Input
                                            id="senderName"
                                            type="text"
                                            placeholder="Nguyễn Huy Hòa - 266/58, Tôn Đản..."
                                            className="w-full"
                                        />
                                    </div>
                                </form>
                            </div>
                            <div className="border-2 border-red-500 p-4 mt-4">
                                <h2 className="font-bold text-lg mb-4">NGƯỜI NHẬN</h2>
                                <form>
                                    <div className="mb-4">
                                        <Label>Tùy chọn nơi nhận</Label>
                                        <div className="flex gap-4">
                                            <label>
                                                <Input type="radio" name="deliveryMethod" value="address" />
                                                Theo địa chỉ KH nhận
                                            </label>
                                            <label>
                                                <Input type="radio" name="deliveryMethod" value="smartBox" />
                                                Nhận tại từ Smart Box
                                            </label>
                                            <label>
                                                <Input type="radio" name="deliveryMethod" value="postOffice" />
                                                Nhận tại bưu cục
                                            </label>
                                        </div>
                                    </div>

                                    <div className="mb-4">
                                        <Label htmlFor="recipientPhone">Điện thoại</Label>
                                        <Input
                                            id="recipientPhone"
                                            type="tel"
                                            placeholder="Nhập số điện thoại"
                                            className="w-full"
                                        />
                                    </div>

                                    <div className="mb-4">
                                        <Label htmlFor="recipientName">Họ tên</Label>
                                        <Input
                                            id="recipientName"
                                            type="text"
                                            placeholder="Nhập tên người nhận"
                                            className="w-full"
                                        />
                                    </div>

                                    <div className="mb-4">
                                        <Label htmlFor="recipientAddress">Địa chỉ</Label>
                                        <Input
                                            id="recipientAddress"
                                            type="text"
                                            placeholder="Nhập địa chỉ"
                                            className="w-full"
                                        />
                                    </div>
                                    <Button type="submit">Gửi</Button>
                                </form>
                            </div>
                        </div>
                    </div>

                </TabsContent>
                <TabsContent value="outside">
                </TabsContent>
            </Tabs>
        </LayoutDashboard>
    )
}