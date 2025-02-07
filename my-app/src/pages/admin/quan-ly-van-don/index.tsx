import LayoutDashboard from "@/components/LayoutDashboard";
import { Separator } from "@/components/ui/separator";
import { useAuth } from "@/context/app.context";
import { OrdersTable } from "@/modules/manage/order/components/OrdersTable";
import Pagination from "@/modules/manage/order/components/Pagination";
import { SearchBar } from "@/modules/manage/order/components/SearchBar";
import { TabsStatus } from "@/modules/manage/order/components/TabsStatus";
import http from "@/utils/http";
import { jwtDecode, JwtPayload } from "jwt-decode";
import { useEffect, useState } from "react";

const ROLES = {
    ADMIN: "ROLE_ADMIN",
    STAFF: "staff",
    CUSTOMER: "customer"
};

export default function Order() {

    const { accessToken } = useAuth();
    const [userInfo, setUserInfo] = useState<JwtPayload | null>(null);
    const [orders, setOrders] = useState<any[]>([]);

    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(20);
    const [totalPages, setTotalPages] = useState<number>(1);

    useEffect(() => {
        if (accessToken) {
            try {
                const decodedToken = jwtDecode(accessToken);
                setUserInfo(decodedToken);
            } catch (error) {
                console.error("Lỗi giải mã token:", error);
            }
        }
    }, [accessToken]);

    useEffect(() => {
        if (userInfo) {
            const fetchOrders = async () => {
                try {
                    let url = `http://localhost:8086/order/orders?page=${page}&size=${size}`;
                    if (!userInfo.scope.includes(ROLES.ADMIN)) {
                        url = `http://localhost:8086/order/orders?page=${page}&size=${size}`;
                    }
    
                    const res = await http.get(url);
    
                    if (res.status === 200) {
                        // console.log("Orders: ", res.data._embedded.orders);
                        setOrders(res.data._embedded.orders);
                        setTotalPages(res.data.page.totalPages);
                    } else {
                        console.error("Failed to fetch orders:", res.status);
                    }
                } catch (error) {
                    console.error("Error fetching orders:", error);
                }
            };
    
            fetchOrders();
        }
    }, [userInfo , page , size] );

    const handlePageChange = (newPage: number) => {
        if (newPage >= 0 && newPage < totalPages) {
            setPage(newPage);
        }
    };

    return (
        <LayoutDashboard>
            <div className="m-3">
                <SearchBar />
                <Separator className="m-2"></Separator>
                <TabsStatus />
                <Separator className="m-2"></Separator>
                <OrdersTable  orders={orders}/>
                <Separator className="m-2"></Separator>
                <Pagination currentPage={page} totalPages={totalPages} onPageChange={handlePageChange} />
            </div>
        </LayoutDashboard>
    )
}