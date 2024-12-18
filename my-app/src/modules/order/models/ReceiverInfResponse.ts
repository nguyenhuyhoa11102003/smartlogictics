export interface ReceiverInfResponse {
    id: string; // ID của người nhận (tự sinh).
    customerId: string; // ID của khách hàng.
    fullName: string; // Tên đầy đủ của người nhận.
    phoneNumber: string; // Số điện thoại của người nhận.
    email: string; // Email liên hệ.
    province: string; // Tỉnh.
    district: string; // Huyện.
    ward: string; // Xã/Phường.
    street: string; // Đường.
    postalCode: string; // Mã bưu chính.
}
