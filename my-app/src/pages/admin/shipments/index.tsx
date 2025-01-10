import LayoutDashboard from '@/components/LayoutDashboard/LayoutDashboard';
import { Shipment } from '@/modules/shipment/models/Shipment';
import ShipmentStatus from '@/modules/shipment/models/ShipmentStatus';
import { vehicles, warehouses } from '@/utils/utils';
import 'bootstrap/dist/css/bootstrap.min.css';

import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Button, Table, Form, Modal } from 'react-bootstrap';
import ReactPaginate from 'react-paginate';

import { Truck, Plus, Minus, Clock, MapPin } from 'lucide-react';
import Link from 'next/link';
import { CreateShipmentRequest } from '@/modules/shipment/dto/request/CreateShipmentRequest';
import CreateShipmentSegmentRequest from '@/modules/shipment/dto/request/CreateShipmentSegmentRequest';


const ShipmentManagement = () => {
    const [showModal, setShowModal] = useState(false);
    const [shipments, setShipments] = useState<Shipment[]>([]);
    const [newShipment, setNewShipment] = useState<CreateShipmentRequest>({
        trackingNumber: "",
        shipper: 0,
        shipmentMethod: "road",
        fromWarehouseId: 0,
        intermediateWarehouseIds: [],
        toWarehouseId: 0,
        shipmentStatus: ShipmentStatus.PENDING,
        departureTime: new Date().toISOString(),
        orders: [],
        shipmentSegmentRequests: [],
        vehicle: {
            id: 0,
            name: "",
            employee: { id: 0, name: "", role: "" }
        }
    });

    useEffect(() => {
        const fetchShipmentDetails = async () => {
            try {
                const response = await fetch(`/api/shipments`);
                const data: Shipment[] = await response.json();
                if (data) {
                    setShipments((prevShipments) => [...prevShipments, ...data]);
                }
            } catch (err) {
                console.error("Error" + err)
            }
        };
        fetchShipmentDetails();
    }, []);

    const handleAddShipment = async () => {
        console.log(newShipment)
        // const { fromWarehouseId, toWarehouseId, intermediateWarehouseIds } = newShipment;
        // if (fromWarehouseId === 0 || toWarehouseId === 0) {
        //     alert("Both fromWarehouseId and toWarehouseId must be selected.");
        //     return;
        // }

        // if (fromWarehouseId === toWarehouseId) {
        //     alert("From warehouse and To warehouse must be different.");
        //     return;
        // }

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

        // check validate
        // setShipments([...shipments, newShipment]);
        // setNewShipment({
        //     id: 1,
        //     trackingNumber: "TN001",
        //     shipper: 501,
        //     shipmentMethod: "road",
        //     fromWarehouse: {
        //         id: 0,
        //         name: "",
        //         warehouseType: "",
        //         address: "",
        //         region: "",
        //         phoneNumber: "",
        //         status: ""
        //     },
        //     intermediateWarehouses: [{
        //         id: 0,
        //         name: "",
        //         warehouseType: "",
        //         address: "",
        //         region: "",
        //         phoneNumber: "",
        //         status: ""
        //     }],
        //     toWarehouse: {
        //         id: 0,
        //         name: "",
        //         warehouseType: "",
        //         address: "",
        //         region: "",
        //         phoneNumber: "",
        //         status: ""
        //     },
        //     shipmentStatus: ShipmentStatus.IN_TRANSIT,
        //     shipmentStartDate: "2024-12-10T00:00:00",
        //     estimatedDeliveryDate: "2024-12-15T00:00:00",
        //     orders: ["ORD-001", "ORD-002"],
        //     originName: '',
        //     destination: '',
        //     shipmentWeight: 0,
        //     shipmentVolume: 0,
        //     vehicle: {
        //         id: 0,
        //         name: "",
        //         employee: { id: 0, name: "", role: "" }
        //     }
        // });
        // setShowModal(false);
        // alert('Add shipment successfull')

    };

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

    return (
        // <LayoutDashboard>
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
                            <td>{shipment.fromWarehouse.name}</td>
                            <td>{shipment.toWarehouse.name}</td>
                            <td>{shipment.shipmentStatus}</td>
                            <td>{new Date(shipment.shipmentStartDate).toLocaleDateString()}</td>
                            <td>{new Date(shipment.estimatedDeliveryDate).toLocaleDateString()}</td>
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
                                        value={newShipment.shipmentMethod}
                                        onChange={(e) => setNewShipment({
                                            ...newShipment,
                                            shipmentMethod: e.target.value
                                        })}
                                    >
                                        <option value="road">Đường bộ</option>
                                        <option value="air">Máy bay (hỏa tốc)</option>
                                    </Form.Select>
                                </Form.Group>
                            </Col>
                        </Row>
                        {/* Chọn xe (only for road transport) */}
                        {newShipment.shipmentMethod === 'road' && (
                            <Row className="mb-3">
                                <Col>
                                    <Form.Group>
                                        <Form.Label className="fw-medium mb-3 d-flex align-items-center gap-2">Chọn xe</Form.Label>
                                        <Form.Select
                                            value={newShipment.vehicle.id}
                                            onChange={(e) => {
                                                const id = Number(e.target.value);
                                                const selectedVehicel = vehicles.find(a => a.id === id)
                                                if (selectedVehicel) {
                                                    setNewShipment({
                                                        ...newShipment,
                                                        vehicle: selectedVehicel
                                                    })
                                                }
                                            }}
                                        >
                                            <option value={0}>Chọn xe</option>
                                            {vehicles.map((vehicle) => (
                                                <option key={vehicle.id} value={vehicle.id}>
                                                    {vehicle.name} - {vehicle.employee.name} ({vehicle.employee.role})
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
                                        <Form.Control
                                            type="datetime-local"
                                            value={newShipment.departureTime}
                                            onChange={(e) => {
                                                setNewShipment((prevState) => ({
                                                    ...prevState,
                                                    shipmentStartDate: e.target.value,
                                                }));
                                            }}
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
                                                        shipmentSegmentRequests: [...prevState.shipmentSegmentRequests, {
                                                            destinationWarehouseId: selectedWarehouseId,
                                                            stopoverDuration: 0
                                                        }],
                                                        intermediateWarehouseIds: [...prevState.intermediateWarehouseIds,selectedWarehouseId]
                                                    }))
                                                }}>
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

                                                    }} />
                                            </Form.Group>
                                        </div>
                                        <button
                                            type="button"
                                            onClick={() => { }}
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
        // </LayoutDashboard>

    );
};

export default ShipmentManagement;