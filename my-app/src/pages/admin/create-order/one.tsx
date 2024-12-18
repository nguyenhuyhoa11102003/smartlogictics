import LayoutDashboard from "@/components/LayoutDashboard";
import OrderForm from "@/modules/order/components/OrderForm";

export default function Dashboard() {
    return (
        <div>
            <LayoutDashboard>
                <OrderForm/>
            </LayoutDashboard>
        </div>
    )
}