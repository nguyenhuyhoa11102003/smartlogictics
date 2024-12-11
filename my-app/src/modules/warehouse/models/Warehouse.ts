export interface Warehouse {
    id: number; // Unique identifier of the warehouse
    name: string; // Name of the warehouse or company
    addressDetail: string; // Full address detail
    phoneNumber: string; // Contact number
    capacity: number; // Capacity of the warehouse
    status: string; // Status of the warehouse (e.g., ACTIVE, INACTIVE)
    address: {
        id: number; // Unique identifier of the address
        province: string; // Province or city
        ward: string; // District or ward
        commune: string; // Commune or sub-district
        street: string; // Street address
        postalCode: string; // Postal code
        addressDetail: string; // Full address detail
    };
    createdAt?: string | null; // Timestamp when the warehouse was created
    updatedAt: string; // Timestamp when the warehouse was last updated
}