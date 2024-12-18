export interface PaginatedResponse<T> {
    content: T[]; // Mảng các phần tử kiểu T
    page: number; // Số trang hiện tại
    size: number; // Kích thước của mỗi trang
    totalElements: number; // Tổng số phần tử
    totalPages: number; // Tổng số trang
    lastPage: boolean; // Xác định có phải trang cuối cùng hay không
}
