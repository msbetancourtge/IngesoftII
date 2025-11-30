import { productsApi } from "../api/productsApi";
import User from "../interface/user";

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
    if (!response || !response.data) return null;

    const user: User & { token?: string } = {
        username: response.data.username,
        role: response.data.role as any,
        token: response.data.token,
    };

    return {
        user,
        message: response.message,
    };
};

export const authLogin = async (username: string, password: string) => {
    try {
       const { data } = await productsApi.post<AuthLoginResponse>('/api/auth/login', {
        username,
        password,
       });

             const result = returnUserToken(data);
             if (result && result.user.token) {
                 localStorage.setItem('token', result.user.token);
                 if (result.user.username) localStorage.setItem('username', result.user.username);
                 if (result.user.role) localStorage.setItem('role', String(result.user.role));
             }

             return result;
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

                // backend returns only a message; return the created user info and message
        const user: User = {
            username,
            email,
            name,
            role: role as any,
        };

        return {
            user,
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
        const { data } = await productsApi.get<AuthResponse>('/auth/check-status');

        return returnUserToken(data);
    } catch (error) {

        return null;
    }

};