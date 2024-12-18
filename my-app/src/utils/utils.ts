import axios, { AxiosError, HttpStatusCode } from 'axios'
import config from '@/constants/config'

// import userImage from 'src/assets/images/user.svg'
import { ErrorResponse } from '@/types/utils.type'

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
