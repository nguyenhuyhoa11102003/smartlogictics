import { Shipment } from "@/modules/shipment/models/Shipment";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Container, Spinner } from "react-bootstrap"; // Import Bootstrap components
import LayoutDashboard from '@/components/LayoutDashboard';
import ShippingDetails from "@/modules/shipment/components/ShippingDetails";
import http from "@/utils/http";

const ShipmentDetail = () => {
    const { query } = useRouter();
    const { id } = query;
    const [shipment, setShipment] = useState<Shipment | null>(null);

    useEffect(() => {
        if (id) {

            const fetchShipmentDetails = async () => {
                const response = await http.get(`http://localhost:8087/shipment-service/shipments/detail/${id}`);
                setShipment(response.data.result as Shipment); 
            }

            fetchShipmentDetails();
        }
    }, [id]);

    if (!shipment) {
        return (
            <LayoutDashboard>
                <Container className="text-center mt-5">
                    <Spinner animation="border" role="status" />
                    <span className="ms-2">Loading...</span>
                </Container>
            </LayoutDashboard>
        );
    }

    return (
        <LayoutDashboard>
            <div className="container mx-auto mt-5">
                <div className="col">
                    <ShippingDetails shipment={shipment}></ShippingDetails>
                </div>
            </div>
        </LayoutDashboard>
    );
};

export default ShipmentDetail;

