export interface Warehouse {
    id: number; // Mã kho
    name: string; // Tên kho
    warehouseType: string; // Loại kho
    address: string; // Địa chỉ kho
    region: string; // Khu vực kho
    phoneNumber: string; // Số điện thoại liên hệ
    capacity?: number; // Diện tích kho (m2)
    storageConditions?: string; // Điều kiện lưu trữ trong kho
    managerName?: string; // Tên người quản lý kho
    managerContact?: string; // Liên hệ người quản lý kho
    operatingHours?: string; // Giờ hoạt động của kho
    availableSpace?: number; // Diện tích còn trống (m2)
    status: string; // Status of the warehouse (e.g., ACTIVE, INACTIVE)
    addressDetail?: {
        id: number; // Unique identifier of the address
        province: string; // Province or city
        ward: string; // District or ward
        commune: string; // Commune or sub-district
        street: string; // Street address
        postalCode: string; // Postal code
        addressDetail: string; // Full address detail
    };
    createdAt?: string | null; // Timestamp when the warehouse was created
    updatedAt?: string; // Timestamp when the warehouse was last updated
}