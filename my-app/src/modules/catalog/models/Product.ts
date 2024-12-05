// export type Product = {
//   id: number;
//   name: string;
//   shortDescription: string;
//   description: string;
//   specification: string;
//   sku: string;
//   gtin: string;
//   slug: string;
//   metaKeyword: string;
//   metaDescription: string;
//   thumbnailMediaUrl: string;
// };


export interface Product {
  productType: string; // Loại sản phẩm, ví dụ: "buu kien"
  productName: string; // Tên sản phẩm
  weight: number; // Trọng lượng sản phẩm (đơn vị: kg hoặc gram)
  quantity: number; // Số lượng sản phẩm
  value: number; // Giá trị sản phẩm (đơn vị: VNĐ hoặc đơn vị tiền tệ)
  totalWeight: number; // Tổng trọng lượng (tính theo quantity * weight)
  totalValue: number; // Tổng giá trị (tính theo quantity * value)
  specialCharacteristics: SpecialCharacteristics; // Các đặc tính đặc biệt
  dimensions: Dimensions; // Kích thước sản phẩm (dài, rộng, cao)
  orderId: string; // Mã đơn hàng liên quan
}


interface SpecialCharacteristics {
  highValue: boolean; // Giá trị cao
  fragile: boolean; // Dễ vỡ
  solidBlock: boolean; // Khối rắn
  oversized: boolean; // Kích thước lớn
  liquid: boolean; // Chất lỏng
  magnetic: boolean; // Từ tính
  coldGoods: boolean; // Hàng hóa cần giữ lạnh
}


interface Dimensions {
  length: number; // Chiều dài (đơn vị: cm)
  width: number; // Chiều rộng (đơn vị: cm)
  height: number; // Chiều cao (đơn vị: cm)
}