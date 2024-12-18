"use client";

import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";

export function TabsStatus() {
    return (
        <Tabs defaultValue="all" className="mb-4">
            <TabsList>
                <TabsTrigger value="all">Tổng số đơn (2)</TabsTrigger>
                <TabsTrigger value="success">Lấy thành công (0)</TabsTrigger>
                <TabsTrigger value="pending">Chờ lấy (0)</TabsTrigger>
                <TabsTrigger value="canceled">Hủy lấy (2)</TabsTrigger>
                <TabsTrigger value="draft">Đơn nháp (0)</TabsTrigger>
            </TabsList>
        </Tabs>
    );
}
