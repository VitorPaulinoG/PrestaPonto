export interface ApiResponse<T> {
  success: boolean;
  data: T | null;
  message: string;
  timestamp: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  name: string;
  token: string;
}

export interface SignupRequest {
  name: string;
  email: string;
  password: string;
  userType: 'CLIENT' | 'PROVIDER';
}
