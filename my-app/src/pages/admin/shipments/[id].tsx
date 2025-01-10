import { Shipment } from "@/modules/shipment/models/Shipment";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Container, Spinner } from "react-bootstrap"; // Import Bootstrap components
import LayoutDashboard from '@/components/LayoutDashboard';
import ShipmentTracking from '@/modules/shipment/components/ShipmentTracking';
import ShippingDetails from "@/modules/shipment/components/ShippingDetails";

const ShipmentDetail = () => {
    const { query } = useRouter();
    const { id } = query;
    const [shipment, setShipment] = useState<Shipment | null>(null);

    useEffect(() => {
        if (id) {
            const fetchShipmentDetails = async () => {
                const response = await fetch(`/api/shipments/${id}`);
                const data: Shipment = await response.json();
                setShipment(data);
            };

            fetchShipmentDetails();
        }
    }, [id]);

    if (!shipment) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" role="status" />
                <span className="ms-2">Loading...</span>
            </Container>
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

