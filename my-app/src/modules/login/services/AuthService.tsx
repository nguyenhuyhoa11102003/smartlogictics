import http from "@/utils/http";
import { LoginRequest } from "../dto/LoginRequest";
import { AuthResponse } from "../dto/auth.type";

export const URL_LOGIN = 'login'
export const URL_REGISTER = 'register'
export const URL_LOGOUT = 'logout'
export const URL_REFRESH_TOKEN = 'refresh-access-token'

const URL_INDENTITY_SERVICE = "http://localhost:8085/identity/api/v1/auth/"


const authApi = {
    async loginAccount(body: LoginRequest, baseUrl: string = URL_INDENTITY_SERVICE) {
        http.defaults.baseURL = baseUrl
        return http.post<AuthResponse>(URL_LOGIN, body)
    }
}

export default authApi;