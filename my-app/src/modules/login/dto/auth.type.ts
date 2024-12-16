import { SuccessResponse } from '@/types/utils.type'

export type AuthResponse = SuccessResponse<{
    refreshToken: string
    token: string
    expires: number
    expires_refresh_token: number
    // user: User
}>

export type RefreshTokenRespone = SuccessResponse<{
    access_token: string
}>
