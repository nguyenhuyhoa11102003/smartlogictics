"use client";

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Separator } from "@/components/ui/separator";

export function SearchBar() {
    return (
        <div>
            <div className="flex gap-4 mb-4">
                <Input placeholder="Tìm đơn hàng, s..." className="w-1/5" />
                <Input type="date" className="w-1/5" />
                <Select>
                    <SelectTrigger className="w-1/5">
                        <SelectValue placeholder="Tất cả kho hàng" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="all">Tất cả</SelectItem>
                    </SelectContent>
                </Select>
                <Select>
                    <SelectTrigger className="w-1/5">
                        <SelectValue placeholder="Chọn theo dịch vụ" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="service1">Người Gửi</SelectItem>
                        <SelectItem value="service2">Người Nhận</SelectItem>
                    </SelectContent>
                </Select>
            </div>

            <Separator className="m-2 bg-white"></Separator>
            <p>Cập nhật danh sách lần cuối lúc 19:00</p>
            <Separator className="m-2"></Separator>

            <div className="flex gap-4 mb-4">
                <Button>IN ĐƠN</Button>
                <Button variant="outline">EXPORT EXCEL</Button>
                <Button variant="outline">IMPORT EXCEL</Button>
            </div>
        </div>

    );
}
