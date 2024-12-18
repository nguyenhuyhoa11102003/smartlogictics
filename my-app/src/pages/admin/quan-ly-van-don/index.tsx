import LayoutDashboard from "@/components/LayoutDashboard";
import { Separator } from "@/components/ui/separator";
import { OrdersTable } from "@/modules/manage/order/components/OrdersTable";
import { Pagination } from "@/modules/manage/order/components/Pagination";
import { SearchBar } from "@/modules/manage/order/components/SearchBar";
import { TabsStatus } from "@/modules/manage/order/components/TabsStatus";

export default function Order() {
    return (
        <LayoutDashboard>
            <div className="m-3">
                <SearchBar />
                <Separator className="m-2"></Separator>
                <TabsStatus />
                <Separator className="m-2"></Separator>
                <OrdersTable />
                <Separator className="m-2"></Separator>
                <Pagination />
            </div>
        </LayoutDashboard>
    )
}