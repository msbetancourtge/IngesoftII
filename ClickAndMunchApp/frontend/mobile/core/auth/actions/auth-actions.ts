import { productsApi } from "../api/productsApi";

// Generic API Response wrapper
interface ApiResponse<T> {
  message: string;
  data: T | null;
}

// Login response data
interface LoginResponseData {
  token: string;
  username: string;
  role: string;
}

// Register response (data is null, just check message)
interface RegisterResponseData {
  // null
}

// Combined AuthResponse type
type AuthResponse = ApiResponse<LoginResponseData>;
type RegisterResponse = ApiResponse<null>;

// Or more specifically:
interface AuthLoginResponse {
  message: string;
  data: {
    token: string;
    username: string;
    role: string;
  } | null;
}

interface AuthRegisterResponse {
  message: string;
  data: null;
}

const returnUserToken = (response: AuthLoginResponse) => {

    if (response.data) {
        return {
            token: response.data.token,
            username: response.data.username,
            role: response.data.role,
            message: response.message,
        };
    }

    return null;
};

export const authLogin = async (username: string, password: string) => {
    try {
       const { data } = await productsApi.post<AuthLoginResponse>('/api/auth/login', {
        username,
        password,
       });

       return returnUserToken(data);
    } catch (error) {
        console.log(error);
        return null;
    }
};

export const authRegister = async (
    name: string,
    email: string,
    username: string,
    password: string,
    role: string = 'USER'
) => {
    try {
        const { data } = await productsApi.post<AuthRegisterResponse>('/api/auth/register', {
            name,
            email,
            username,
            password,
            role,
        });

        return {
            success: data.message.toLowerCase().includes('success'),
            message: data.message,
        };
    } catch (error) {
        console.log(error);
        return {
            success: false,
            message: 'Registration failed',
        };
    }
};

export const authCheckStatus = async() => {
    try {
        const { data } = await productsApi.post<AuthLoginResponse>('/api/auth/login', {
            username: localStorage.getItem('username'),
            password: '', // This won't work - need a better approach
        });

        return returnUserToken(data);
    } catch (error) {
        return null;
    }
};