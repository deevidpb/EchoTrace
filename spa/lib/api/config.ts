export const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_URL ?? "http://127.0.0.1:8080"

export const AUTH_LOGIN_URL = `${API_BASE_URL}/oauth2/authorization/spotify`
export const AUTH_LOGOUT_URL = `${API_BASE_URL}/api/auth/logout`
export const AUTH_CHECK_URL = `${API_BASE_URL}/api/web/auth/check`;


