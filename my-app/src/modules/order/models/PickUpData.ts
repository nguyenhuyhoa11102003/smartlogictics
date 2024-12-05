export interface PickupData {
    sender: string; // Name or identifier of the sender
    pickupLocation: string; // "Nhận tại nhà" or selected post office
    pickupDate: string; // Formatted pickup date with time period
    postOfficeId?: string; // Selected post office ID if applicable
    pickupDay: string; // Selected day for pickup
    timePeriod: string; // Time period (morning, afternoon, evening)
    isPostOfficePickup: boolean; // Whether pickup is at a post office
}