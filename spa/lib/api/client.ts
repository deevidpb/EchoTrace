import { API_BASE_URL } from "./config"

export class ApiError extends Error {
  constructor(
    message: string,
    public status: number,
  ) {
    super(message)
    this.name = "ApiError"
  }
}

function getCookie(name: string): string | undefined {
  return document.cookie
      .split("; ")
      .find(cookie => cookie.startsWith(`${name}=`))
      ?.split("=")[1];
}

export async function apiFetch<T>(path: string, init?: RequestInit): Promise<T> {
  const csrfToken = getCookie("XSRF-TOKEN");
  const res = await fetch(`${API_BASE_URL}${path}`, {
    ...init,
    credentials: "include",
    headers: {
      Accept: "application/json",
      ...(csrfToken ? { "X-XSRF-TOKEN": csrfToken } : {}),
      ...init?.headers,
    },
  })

  if (!res.ok) {
    const body = await res.text().catch(() => "")
    throw new ApiError(body || res.statusText, res.status)
  }

  if (res.status === 204) return undefined as T
  return res.json() as Promise<T>
}
