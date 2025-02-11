import LayoutDashboard from '@/components/LayoutDashboard/LayoutDashboard';
import { Shipment } from '@/modules/shipment/models/Shipment';
import ShipmentStatus from '@/modules/shipment/models/ShipmentStatus';
import 'bootstrap/dist/css/bootstrap.min.css';

import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Button, Table, Form, Modal } from 'react-bootstrap';
import ReactPaginate from 'react-paginate';

import { Truck, Plus, Minus, Clock, MapPin } from 'lucide-react';
import Link from 'next/link';
import { CreateShipmentRequest } from '@/modules/shipment/dto/request/CreateShipmentRequest';
import { jwtDecode, JwtPayload } from "jwt-decode";


// react datepicker
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { set, setHours, setMinutes } from "date-fns";
import { Warehouse } from '@/modules/warehouse/models/Warehouse';
import { getAllWarehouses } from '@/modules/warehouse/services/WarehouseService';
import { useAuth } from '@/context/app.context';
import { generateTrackingNumber } from '@/utils/utils';
import axios from 'axios';
import http from '@/utils/http';


const ShipmentManagement = () => {

    const { accessToken } = useAuth();
    const [userInfo, setUserInfo] = useState<JwtPayload | null>(null);
    const [shipments, setShipments] = useState<Shipment[]>([]);
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);
    const [showModal, setShowModal] = useState(false);
    const [shipmentMethod, setShipmentMethod] = useState<string>('TRUCK');
    const [shippers, setShippers] = useState<any[]>([]);

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

    const [newShipment, setNewShipment] = useState<CreateShipmentRequest>({
        trackingNumber: generateTrackingNumber(),
        shipmentMethod: "TRUCK",
        fromWarehouseId: 0,
        toWarehouseId: 0,
        shipmentStatus: ShipmentStatus.PENDING,
        departureTime: new Date().toISOString(),
        orders: [],
        shipmentSegmentRequests: [],

        shipper: "",
        // vehicle: {
        //     id: 0,
        //     name: "",
        //     employee: { id: 0, name: "", role: "" }
        // }

    });

    const [startDate, setStartDate] = useState(
        setHours(setMinutes(new Date(), 0), 9),
    );

    const filterPassedTime = (time: Date): boolean => {
        const currentDate = new Date();
        const selectedDate = new Date(time);

        return currentDate.getTime() < selectedDate.getTime();
    };

    useEffect(() => {
        const fetchShipments = async () => {
            try {
                const url = `http://localhost:8087/shipment-service/shipments/all?page=0&size=10&sortBy=createAt&sortDir=asc`;
                const res = await axios.get(url, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${accessToken}`
                    }
                });

                if (res.status === 200) {
                    const data = res.data.result.content;
                    console.log("Shipment: ", JSON.stringify(data));
                    const newData: Shipment[] = data.map((shipment: any) => ({
                        id: shipment.id,
                        trackingNumber: shipment.trackingNumber,
                        shipmentMethod: shipment.shipmentMethod,
                        fromWarehouse: shipment.fromWarehouse,
                        toWarehouse: shipment.toWarehouse,
                        shipmentStatus: shipment.shipmentStatus,
                        departureTime: shipment.departureTime,
                        estimatedDeliveryDate: shipment.arrivalTime,
                        orders: [],
                        shipmentSegmentRequests: [],
                        shipper: "",
                    }));
                    // console.log("Shipmentssssss: ", newData);
                    setShipments((prevShipments) => [...prevShipments, ...data]);
                }



                // const response = await fetch(`http://localhost:8087/shipment-service/shipments/all?page=0&size=10&sortBy=createAt&sortDir=asc`, {
                //     method: 'GET',
                //     headers: {
                //         'Content-Type': 'application/json',
                //         'Authorization': `Bearer ${accessToken}`
                //     }
                // });
                // const data: Shipment[] = await response.json();
                // if (data) {
                //     // setShipments((prevShipments) => [...prevShipments, ...data]);
                // }
            } catch (err) {
                console.error("Error" + err)
            }
        };
        fetchShipments();
    }, [accessToken]);

    useEffect(() => {
        const fetchWarehouses = async () => {
            try {
                const response: Warehouse[] = await getAllWarehouses();
                if (response) {
                    setWarehouses((prevWarehouses) => [...prevWarehouses, ...response]);
                }
            } catch (err) {
                console.error("Error" + err)
            }
        }
        fetchWarehouses();
    }, []);

    const handleAddShipment = async () => {
        const { fromWarehouseId, toWarehouseId, intermediateWarehouseIds } = newShipment;
        if (fromWarehouseId === 0 || toWarehouseId === 0) {
            alert("Both fromWarehouseId and toWarehouseId must be selected.");
            return;
        }

        if (fromWarehouseId === toWarehouseId) {
            alert("From warehouse and To warehouse must be different.");
            return;
        }

        // if (intermediateWarehouseIds.length > 0) {
        //     const hasDuplicate = new Set(intermediateWarehouseIds).size !== intermediateWarehouseIds.length;
        //     if (hasDuplicate) {
        //         alert("Intermediate warehouses must not contain duplicates.");
        //         return;
        //     }
        //     const invalidIntermediate = intermediateWarehouseIds.some((id) => id === fromWarehouseId || id === toWarehouseId);
        //     if (invalidIntermediate) {
        //         alert("Intermediate warehouses must not overlap with fromWarehouseId or toWarehouseId.");
        //         return;
        //     }
        // }

        console.info("ADD NEW SHIPMENT");

        const payload = {
            ...newShipment,
            shipmentMethod: convertShipmentMethod(shipmentMethod),
            departureTime: startDate.toISOString()
        }

        // console.info("Payload: ", JSON.stringify(payload));

        if (payload.shipper === "") {
            alert("Chưa chọn phương tiện vận chuyển");
            return;
        }

        const res = await http.post('http://localhost:8087/shipment-service/shipments/create', JSON.stringify(payload));
        if(res.data.code === 201){
            alert("Thêm vận chuyển thành công");
            setShowModal(false);    
        }        
        else{
            alert("Thêm vận chuyển thất bại");
        }

    };

    const convertShipmentMethod = (method: string) => {
        switch (method) {
            case 'TRUCK':
                return 'XE_TAI';
            case 'MOTORBIKE':
                return 'XE_MAY  ';
            case 'AIR':
                return 'MAY_BAY';
            default:
                return 'Không xác định';
        }
    }


    // Add intermediate stop
    const addIntermediateStop = () => {
        setNewShipment((prevState) => ({
            ...prevState,
            shipmentSegmentRequests: [
                ...prevState.shipmentSegmentRequests,
                {
                    destinationWarehouseId: -1,
                    stopoverDuration: 0
                }
            ]
        }));
    };

    useEffect(() => {

        const fetchVehicles = async () => {
            const res = await fetch(`http://localhost:8082/users/api/shipper/search/findByVehicleType?vehicleType=${shipmentMethod}`);

            if (res.status === 200) {
                const data = await res.json();
                setShippers(data._embedded.shipper);
            }
        };
        fetchVehicles();

    }, [shipmentMethod]);

    return (
        <LayoutDashboard>
        <Container fluid className="p-4">
            {/* Header */}
            <Row className="mb-4 align-items-center">
                <Col>
                    <h2>Quản lý Vận Chuyển</h2>
                </Col>
                <Col xs="auto">
                    <Button variant="primary" onClick={() => setShowModal(true)}>
                        + Thêm Vận Chuyển Mới
                    </Button>
                </Col>
            </Row>
            {/* Search & Filter */}
            <Row className="mb-4 g-3">
                <Col>
                    <Form.Control
                        type="text"
                        placeholder="Tìm theo mã theo dõi..."
                    />
                </Col>
                <Col>
                    <Form.Control
                        type="date"
                        placeholder="Ngày nhập"
                    />
                </Col>
                <Col>
                    <Form.Select>
                        <option value="">Trạng thái</option>
                        <option value={ShipmentStatus.PENDING}>Đang chờ</option>
                        <option value={ShipmentStatus.IN_TRANSIT}>Đang vận chuyển</option>
                        <option value={ShipmentStatus.DELIVERED}>Đã giao</option>
                    </Form.Select>
                </Col>
                <Col>
                    <Form.Select>
                        <option value="">Phương thức vận chuyển</option>
                        <option value="road">Đường bộ</option>
                        <option value="air">Đường không</option>
                        <option value="sea">Đường biển</option>
                    </Form.Select>
                </Col>
                <Col xs="auto">
                    <Button variant="success">Áp dụng</Button>
                </Col>
            </Row>
            {/* Main Table */}
            <Table responsive striped hover>
                <thead>
                    <tr>
                        <th>Mã theo dõi</th>
                        <th>Phương thức</th>
                        <th>Kho xuất phát</th>
                        <th>Kho đích</th>
                        <th>Trạng thái</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày dự kiến</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    {shipments.map((shipment) => (
                        <tr key={shipment.id}>
                            <td>{shipment.trackingNumber}</td>
                            <td>{shipment.shipmentMethod}</td>
                            <td>{shipment.fromWarehouse?.name}</td>
                            <td>{shipment.toWarehouse?.name}</td>
                            <td>{shipment.shipmentStatus}</td>
                            <td>{shipment.departureTime}</td>
                            <td>{shipment.arrivalTime}</td>
                            <td>
                                <Link href={`/admin/shipments/${shipment.id}`}>
                                    <Button variant="primary" size="sm" className="me-2">
                                        Chi tiết
                                    </Button>
                                </Link>
                                <Button variant="warning" size="sm" className="me-2">
                                    Sửa
                                </Button>
                                <Button variant="danger" size="sm">
                                    Xóa
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            {/* Phân trang */}
            <Row className="mt-4">
                <Col xs="auto">
                    <ReactPaginate
                        previousLabel={"Trước"}
                        nextLabel={"Sau"}
                        pageCount={5}
                        onPageChange={() => { }}
                        containerClassName={"pagination justify-content-center"}
                        activeClassName={"active"}
                        pageClassName={"page-item"}
                        pageLinkClassName={"page-link"}
                        previousClassName={"page-item"}
                        nextClassName={"page-item"}
                        previousLinkClassName={"page-link"}
                        nextLinkClassName={"page-link"}
                    />
                </Col>
            </Row>
            {/* Add New Shipment Modal */}
            <Modal show={showModal} onHide={() => setShowModal(false)} size="lg">
                <Modal.Header closeButton>
                    <Truck className="w-6 h-6" />
                    <Modal.Title>
                        Thêm Vận Chuyển Mới</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>

                        {/* Phương tiện vận chuyển */}
                        <Row className="mb-3">
                            <Col>
                                <Form.Group>
                                    <Form.Label className="fw-medium mb-3 d-flex align-items-center gap-2">
                                        Phương tiện vận chuyển</Form.Label>
                                    <Form.Select
                                        value={shipmentMethod}
                                        onChange={(e) => {
                                            // setNewShipment({
                                            //     ...newShipment,
                                            //     shipmentMethod: e.target.value
                                            // })
                                            setShipmentMethod(e.target.value);
                                        }}
                                    >
                                        <option value="TRUCK">Xe Tải</option>
                                        <option value="MOTORBIKE">Xe máy</option>
                                        <option value="MOTORBIKE">Máy Bay</option>
                                    </Form.Select>
                                </Form.Group>
                            </Col>
                        </Row>

                        {/* Chọn xe */}
                        {shipmentMethod === 'TRUCK' && (
                            <Row className="mb-3">
                                <Col>
                                    <Form.Group>
                                        <Form.Label className="fw-medium mb-3 d-flex align-items-center gap-2">Chọn xe</Form.Label>
                                        <Form.Select
                                            value={newShipment.shipper}
                                            onChange={(e) => {
                                                const id = e.target.value;
                                                const selectedVehicel = shippers.find(a => a.id === id)
                                                if (selectedVehicel) {
                                                    setNewShipment({
                                                        ...newShipment,
                                                        shipper: selectedVehicel.id
                                                    })
                                                }

                                            }}
                                        >
                                            <option value={0}>Chọn xe</option>
                                            {shippers?.map((vehicle) => (
                                                <option key={vehicle.id} value={vehicle.id}>
                                                    {/* {vehicle.name} - {vehicle.employee.name} ({vehicle.employee.role}) */}
                                                    Lisence: {vehicle.licensePlate} - Area: {vehicle.deliveryArea} - Tên: {vehicle.fullName} - SĐT: {vehicle.phoneNumber}
                                                </option>
                                            ))}
                                        </Form.Select>
                                    </Form.Group>
                                </Col>
                            </Row>
                        )}

                        {/* Tracking Number and Shipment Start */}
                        <Row className="mb-3">
                            <Col>
                                <Form.Group>
                                    <Form.Label className="fw-medium mb-3 d-flex align-items-center gap-2">Mã theo dõi</Form.Label>
                                    <Form.Control
                                        type="text"
                                        value={newShipment.trackingNumber}
                                        onChange={(e) => setNewShipment({ ...newShipment, trackingNumber: e.target.value })}
                                    />
                                </Form.Group>
                            </Col>
                            <Col md={6}>
                                <Form.Group>
                                    <Form.Label className="fw-medium mb-3 d-flex align-items-center gap-2">Mã lô hàng</Form.Label>
                                    <Form.Control type="text" placeholder="VD: SHIP-001" value={1} readOnly />
                                </Form.Group>
                            </Col>
                        </Row>

                        {/* Origin Warehouse */}
                        <div className="pb-4">
                            <Row className="d-flex justify-center align-items-center">
                                <Col xs={6} className="text-center">
                                    <h2 className="fw-medium mb-3 d-flex align-items-center gap-2">
                                        <MapPin className="w-5 h-5" />
                                        Kho xuất phát
                                    </h2>
                                </Col>
                                <Col xs={6} className="text-center">
                                    <h2>Thời gian khởi hành</h2>
                                </Col>
                            </Row>
                            <Row className="g-4">
                                <Col md={8}>
                                    <Form.Select
                                        value={newShipment.fromWarehouseId}
                                        onChange={(e) => {
                                            const selectedWarehouseId = Number(e.target.value);
                                            const selectedWarehouse = warehouses.find(warehouse => warehouse.id === selectedWarehouseId);
                                            if (selectedWarehouse != null) {
                                                setNewShipment({
                                                    ...newShipment,
                                                    fromWarehouseId: selectedWarehouseId
                                                });
                                            }
                                        }}
                                    >
                                        <option value={0}>Chọn kho xuất phát</option>
                                        {warehouses.map((warehouse) => (
                                            <option key={warehouse.id} value={warehouse.id}>
                                                {warehouse.name} - {warehouse.address}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Col>
                                <Col md={4}>
                                    <Form.Group>
                                        <DatePicker
                                            selected={startDate} // Current selected date
                                            onChange={(date: Date | null) => {
                                                if (date) {
                                                    setStartDate(date);
                                                    setNewShipment({
                                                        ...newShipment,
                                                        departureTime: date.toISOString()
                                                    });
                                                }
                                            }}
                                            showTimeSelect // Enable time selection
                                            filterTime={filterPassedTime} // Filter out past times
                                            dateFormat="MMMM d, yyyy h:mm aa" // Format for date and time
                                            timeFormat="h:mm aa" // Format for time
                                            timeIntervals={15} // Intervals for time selection (e.g., 15 minutes)
                                            minDate={new Date()} // Prevent selecting past dates
                                            placeholderText="Select a date and time" // Placeholder text
                                            className="custom-date-picker" // Optional: Custom styling class
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>
                        </div>

                        {/* Intermediate Stops */}
                        <div className="border-top pt-4">
                            <div className="d-flex justify-content-between align-items-center mb-3">
                                <h3 className="fw-medium d-flex align-items-center gap-2">
                                    <MapPin className="w-5 h-5" />
                                    Điểm trung gian
                                </h3>
                                <Button
                                    variant="link"
                                    onClick={addIntermediateStop}
                                    className="text-primary d-flex align-items-center gap-1"
                                >
                                    <Plus className="w-4 h-4" />
                                    Thêm điểm dừng
                                </Button>
                            </div>
                            {newShipment.shipmentSegmentRequests.map((stop, index) => (
                                <Row key={index} className="g-4 mb-4">
                                    <Col md={8}>
                                        <Form.Group>
                                            <Form.Label>Chọn kho trung gian</Form.Label>
                                            <Form.Control
                                                as="select"
                                                value={stop.destinationWarehouseId || ""}
                                                onChange={(event) => {
                                                    const selectedWarehouseId = Number(event.target.value);
                                                    setNewShipment((prevState) => ({
                                                        ...prevState,
                                                        shipmentSegmentRequests: prevState.shipmentSegmentRequests.map((segment, idx) => {
                                                            if (idx === index) {
                                                                return {
                                                                    ...segment,
                                                                    destinationWarehouseId: selectedWarehouseId,
                                                                };
                                                            }
                                                            return segment;
                                                        }),
                                                    }));
                                                }}>
                                                <option value="" disabled>Chọn kho trung gian</option>
                                                {warehouses.map((warehouse) => (
                                                    <option key={warehouse.id} value={warehouse.id}>
                                                        {warehouse.name} - {warehouse.address}
                                                    </option>
                                                ))}
                                            </Form.Control>
                                        </Form.Group>
                                    </Col>
                                    <Col md={4} className="d-flex align-items-end gap-2">
                                        <div className="flex-grow-1">
                                            <Form.Group>
                                                <Form.Label>Thời gian dừng (giờ)</Form.Label>
                                                <Form.Control
                                                    type="number"
                                                    min="0"
                                                    value={stop.stopoverDuration}
                                                    onChange={(event) => {
                                                        const newDuration = Number(event.target.value);
                                                        setNewShipment((prevState) => ({
                                                            ...prevState,
                                                            shipmentSegmentRequests: prevState.shipmentSegmentRequests.map((segment, idx) => {
                                                                if (idx === index) {
                                                                    return {
                                                                        ...segment,
                                                                        stopoverDuration: newDuration,
                                                                    };
                                                                }
                                                                return segment;
                                                            }),
                                                        }));

                                                    }} />
                                            </Form.Group>
                                        </div>
                                        <button
                                            type="button"
                                            onClick={() => {
                                                setNewShipment((prevState) => ({
                                                    ...prevState,
                                                    shipmentSegmentRequests: prevState.shipmentSegmentRequests.filter((_, idx) => idx !== index),
                                                }));
                                            }}
                                            className="p-2 text-red-600 hover:text-red-700"
                                        >
                                            <Minus className="w-4 h-4" />
                                        </button>
                                    </Col>
                                </Row>
                            ))}
                        </div>

                        {/* Destination Warehouse */}
                        <div className="border-top pt-4">
                            <Row className="d-flex justify-start align-items-center">
                                <Col xs={6} className="text-center">
                                    <h2 className="fw-medium mb-3 d-flex align-items-center gap-2">
                                        <MapPin className="w-5 h-5" />
                                        Kho Nhận Hàng
                                    </h2>
                                </Col>
                            </Row>
                            <Row className="g-4 pb-4">
                                <Col md={8}>
                                    <Form.Select
                                        value={newShipment.toWarehouseId}
                                        onChange={(e) => {
                                            const selectedWarehouseId = Number(e.target.value);
                                            const selectedWarehouse = warehouses.find(warehouse => warehouse.id === selectedWarehouseId);
                                            if (selectedWarehouse != null) {
                                                setNewShipment({
                                                    ...newShipment,
                                                    toWarehouseId: selectedWarehouseId
                                                });
                                            }
                                        }}
                                    >
                                        <option value={0}>Chọn kho xuất phát</option>
                                        {warehouses.map((warehouse) => (
                                            <option key={warehouse.id} value={warehouse.id}>
                                                {warehouse.name} - {warehouse.address}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Col>
                            </Row>
                        </div>


                        {/* Estimated Time Summary */}
                        <div className="border-top pt-4">
                            <h3 className="fw-medium mb-3 d-flex align-items-center gap-2">
                                <Clock className="w-5 h-5" />
                                Tổng thời gian dự kiến
                            </h3>
                            <div className="bg-light p-4 rounded">
                                <Row className="g-4">
                                    <Col md={6}>
                                        <span className="text-muted">Thời gian di chuyển:</span>
                                        <p className="fw-medium">12 giờ</p>
                                    </Col>
                                    <Col md={6}>
                                        <span className="text-muted">Thời gian dừng:</span>
                                        <p className="fw-medium">2 giờ</p>
                                    </Col>
                                    <Col md={6}>
                                        <span className="text-muted">Tổng quãng đường:</span>
                                        <p className="fw-medium">800 km</p>
                                    </Col>
                                    <Col md={6}>
                                        <span className="text-muted">Tổng thời gian:</span>
                                        <p className="fw-medium">14 giờ</p>
                                    </Col>
                                </Row>
                            </div>
                        </div>


                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Hủy
                    </Button>
                    <Button variant="primary" onClick={handleAddShipment}>
                        Lưu
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container >
        </LayoutDashboard>

    );
};

export default ShipmentManagement;

function uuidv4(): string {
    throw new Error('Function not implemented.');
}
