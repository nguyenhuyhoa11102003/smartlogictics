import axios, { AxiosError, HttpStatusCode } from 'axios'
import config from '@/constants/config'

// import userImage from 'src/assets/images/user.svg'
import { ErrorResponse } from '@/types/utils.type'
import { Warehouse } from '@/modules/warehouse/models/Warehouse'

export function isAxiosError<T>(error: unknown): error is AxiosError<T> {
  // eslint-disable-next-line import/no-named-as-default-member
  return axios.isAxiosError(error)
}

export function isAxiosUnprocessableEntityError<FormError>(error: unknown): error is AxiosError<FormError> {
  return isAxiosError(error) && error.response?.status === HttpStatusCode.UnprocessableEntity
}

export function isAxiosUnauthorizedError<UnauthorizedError>(error: unknown): error is AxiosError<UnauthorizedError> {
  return isAxiosError(error) && error.response?.status === HttpStatusCode.Unauthorized
}

export function isAxiosExpiredTokenError<UnauthorizedError>(error: unknown): error is AxiosError<UnauthorizedError> {
  return (
    isAxiosUnauthorizedError<ErrorResponse<{ name: string; message: string }>>(error) &&
    error.response?.data?.data?.name === 'EXPIRED_TOKEN'
  )
}

export function formatCurrency(currency: number) {
  return new Intl.NumberFormat('de-DE').format(currency)
}

export function formatNumberToSocialStyle(value: number) {
  return new Intl.NumberFormat('en', {
    notation: 'compact',
    maximumFractionDigits: 1
  })
    .format(value)
    .replace('.', ',')
    .toLowerCase()
}

export const rateSale = (original: number, sale: number) => Math.round(((original - sale) / original) * 100) + '%'

const removeSpecialCharacter = (str: string) =>
  // eslint-disable-next-line no-useless-escape
  str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g, '')

export const generateNameId = ({ name, id }: { name: string; id: string }) => {
  return removeSpecialCharacter(name).replace(/\s/g, '-') + `-i-${id}`
}

export const getIdFromNameId = (nameId: string) => {
  const arr = nameId.split('-i-')
  return arr[arr.length - 1]
}

// export const getAvatarUrl = (avatarName?: string) => {
//   if (avatarName) {
//     return `${config.baseUrl}images/${avatarName}`
//   }
//   return userImage
// }



export const formatToVietnamTime = (isoString: string) => {
  const date = new Date(isoString);
  return new Intl.DateTimeFormat('vi-VN', {
    timeZone: 'Asia/Ho_Chi_Minh', // Múi giờ, ví dụ: 'Asia/Ho_Chi_Minh' cho Việt Nam.
    year: 'numeric', // Định dạng năm: 'numeric' (2024) hoặc '2-digit' (24).
    month: '2-digit', // Định dạng tháng: 'numeric' (12), '2-digit' (12), 'long' (December), 'short' (Dec), 'narrow' (D).
    day: '2-digit', // Định dạng ngày: 'numeric' (10), '2-digit' (10).
    hour: '2-digit', // Định dạng giờ: 'numeric' (22), '2-digit' (22).
    minute: '2-digit', // Định dạng phút: 'numeric' (37), '2-digit' (37).
    second: '2-digit', // Định dạng giây: 'numeric' (36), '2-digit' (36).
    hour12: false // `true` cho AM/PM, `false` cho định dạng 24 giờ.
  }).format(date);
}


export const vehicles = [
  {
      id: 1,
      name: 'Xe tải A',
      employee: { id: 101, name: 'Nguyễn Văn A', role: 'Lái xe' }
  },
  {
      id: 2,
      name: 'Xe tải B',
      employee: { id: 102, name: 'Trần Thị B', role: 'Lái xe' }
  },
  {
      id: 3,
      name: 'Xe tải C',
      employee: { id: 103, name: 'Lê Minh C', role: 'Lái xe' }
  },
];



export const warehouses: Warehouse[] = [
  {
    id: 1,
    name: "Kho A",
    warehouseType: "Kho gom hàng và trung chuyển",
    address: "123 Đường ABC, Phường 1, Quận Hoàn Kiếm, Hà Nội",
    region: "Bắc",
    phoneNumber: "+84 24 1234 5678",
    capacity: 5000, // Diện tích kho (m2)
    storageConditions: "Kho có hệ thống khu vực gom hàng, khu vực phân loại và trung chuyển hàng hóa",
    managerName: "Nguyễn Văn A",
    managerContact: "+84 912 345 678",
    operatingHours: "08:00 - 17:00, Thứ 2 - Thứ 6",
    availableSpace: 3000,
    status: '',
    updatedAt: ''
  },
  {
    id: 2,
    name: "Kho B",
    warehouseType: "Kho gom hàng và trung chuyển",
    address: "456 Đường DEF, Phường Tân Bình, Quận TP.HCM",
    region: "Nam",
    phoneNumber: "+84 28 8765 4321",
    capacity: 8000, // Diện tích kho (m2)
    storageConditions: "Kho có khu vực gom hàng từ nhiều nguồn và khu vực trung chuyển để chuyển tiếp hàng",
    managerName: "Trần Thị B",
    managerContact: "+84 913 456 789",
    operatingHours: "08:30 - 18:00, Thứ 2 - Thứ 7",
    availableSpace: 5000,
    status: '',
    updatedAt: ''
  },
  {
    id: 3,
    name: "Kho C",
    warehouseType: "Kho gom hàng và trung chuyển",
    address: "789 Đường XYZ, Phường Hòa Cường, Quận Đà Nẵng",
    region: "Trung",
    phoneNumber: "+84 236 123 4567",
    capacity: 6000, // Diện tích kho (m2)
    storageConditions: "Kho chuyên gom hàng và trung chuyển qua các tuyến Đà Nẵng, Hà Nội, TP.HCM",
    managerName: "Lê Thị C",
    managerContact: "+84 913 678 901",
    operatingHours: "09:00 - 17:00, Thứ 2 - Chủ Nhật",
    availableSpace: 4000,
    status: '',
    updatedAt: ''
  },
  {
    id: 4,
    name: "Kho D",
    warehouseType: "Kho gom hàng và trung chuyển",
    address: "101 Đường GHI, Phường 3, Quận Cầu Giấy, Hà Nội",
    region: "Bắc",
    phoneNumber: "+84 24 9876 5432",
    capacity: 7000, // Diện tích kho (m2)
    storageConditions: "Kho có khu vực gom hàng từ các nguồn khác nhau, bao gồm nguyên liệu và thành phẩm",
    managerName: "Phạm Văn D",
    managerContact: "+84 912 789 123",
    operatingHours: "07:00 - 16:00, Thứ 2 - Thứ 6",
    availableSpace: 3500,
    status: '',
    updatedAt: ''
  },
];


import { v4 as uuidv4 } from 'uuid';

export const generateTrackingNumber = () => {
  const uuidPart = uuidv4().replace(/-/g, '').slice(0, 8); // 8 ký tự từ UUID
  return `VN-SHIPMENT-${uuidPart}`; // Định dạng tùy chỉnh
}


export const generateShipment = () => {
  const uuidPart = uuidv4().replace(/-/g, '').slice(0, 8); // 8 ký tự từ UUID
  return `SHIP-${uuidPart}`; // Định dạng tùy chỉnh
}


export const vietnamDateTime = () => {
    // Get current date and time in UTC
    const vietnamTime = new Date();
  
    // Adjust to Vietnam time (UTC+7)
    const vietnamOffset = 7 * 60; // Vietnam is UTC+7, so offset is +420 minutes
    
    // Adjust the current UTC time to Vietnam time by adding the offset
    vietnamTime.setMinutes(vietnamTime.getMinutes() + vietnamTime.getTimezoneOffset() + vietnamOffset);
  
    // Get the ISO string and format it to match datetime-local format (YYYY-MM-DDTHH:mm)
    const vietnamDateTime = vietnamTime.toISOString().slice(0, 16);
  
    console.log(vietnamDateTime); // This should print the correct Vietnam local time
    return vietnamDateTime;
}