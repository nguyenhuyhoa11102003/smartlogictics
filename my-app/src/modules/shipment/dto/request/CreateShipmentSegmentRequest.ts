interface CreateShipmentSegmentRequest {
    weatherCondition?: string;
    trafficCondition?: number;
    segmentStatus?: string;
    notes?: string;
    destinationWarehouseId: number
    stopoverDuration: number;
}

export default CreateShipmentSegmentRequest;
