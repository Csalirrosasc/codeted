import axios, { AxiosError, type InternalAxiosRequestConfig } from 'axios';

import { type ApiError, type ApiResponse, type AuthResponse } from '@/types';

const ACCESS_TOKEN_KEY = 'codeted_access_token';
const REFRESH_TOKEN_KEY = 'codeted_refresh_token';
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 15_000,
});

interface RetryableRequestConfig extends InternalAxiosRequestConfig {
  _retry?: boolean;
}

export function getStoredAccessToken(): string | null {
  if (typeof window === 'undefined') {
    return null;
  }

  return localStorage.getItem(ACCESS_TOKEN_KEY);
}

export function getStoredRefreshToken(): string | null {
  if (typeof window === 'undefined') {
    return null;
  }

  return localStorage.getItem(REFRESH_TOKEN_KEY);
}

export function storeSession(auth: AuthResponse): void {
  if (typeof window === 'undefined') {
    return;
  }

  localStorage.setItem(ACCESS_TOKEN_KEY, auth.accessToken);
  localStorage.setItem(REFRESH_TOKEN_KEY, auth.refreshToken);
}

export function clearStoredSession(): void {
  if (typeof window === 'undefined') {
    return;
  }

  localStorage.removeItem(ACCESS_TOKEN_KEY);
  localStorage.removeItem(REFRESH_TOKEN_KEY);
}

apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = getStoredAccessToken();

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

async function refreshAccessToken(): Promise<string | null> {
  const refreshToken = getStoredRefreshToken();

  if (!refreshToken) {
    return null;
  }

  try {
    const response = await axios.post<ApiResponse<AuthResponse>>(
      `${API_BASE_URL}/auth/refresh`,
      { refreshToken },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );

    storeSession(response.data.data);
    return response.data.data.accessToken;
  } catch {
    clearStoredSession();
    return null;
  }
}

function redirectToLogin(): void {
  if (typeof window !== 'undefined' && window.location.pathname !== '/auth/login') {
    window.location.href = '/auth/login';
  }
}

apiClient.interceptors.response.use(
  (response) => response,
  async (error: AxiosError<ApiError>) => {
    const originalRequest = error.config as RetryableRequestConfig | undefined;

    if (error.response) {
      const { status, data } = error.response;

      if (status === 401 && originalRequest && !originalRequest._retry) {
        originalRequest._retry = true;
        const accessToken = await refreshAccessToken();

        if (accessToken) {
          originalRequest.headers.Authorization = `Bearer ${accessToken}`;
          return apiClient(originalRequest);
        }
      }

      if (status === 401) {
        clearStoredSession();
        redirectToLogin();
      }

      return Promise.reject(data ?? error);
    }

    if (error.request) {
      return Promise.reject({
        success: false,
        message: 'Error de red. Verifica tu conexion e intentalo de nuevo.',
        status: 0,
        timestamp: new Date().toISOString(),
      } satisfies ApiError);
    }

    return Promise.reject(error);
  }
);
