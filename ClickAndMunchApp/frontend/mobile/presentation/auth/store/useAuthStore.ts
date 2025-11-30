import User from "@/core/auth/interface/user";
import { create } from 'zustand'

export type AuthStatus = 'authenticated' | 'unauthenticated' | 'checking';

export type AuthState = {
    status: AuthStatus;
    token?: string | null;
    user?: User | null;

    // setters
    setToken: (token: string | null) => void;
    setUser: (user: User | null) => void;

    login: (email: string, password: string) => Promise<boolean>;
    checkStatus: () => Promise<void>;
    logout: () => Promise<void>;
} 

export const useAuthStore = create<AuthState>()( (set, get) => ({

    //Properties
    status: "checking",
    token: null,
    user: null,

    //Setters
    setToken: (token) => set({ token }),
    setUser: (user) => set({ user }),

    //Actions
    login: async (email: string, password: string) => {
        return true;
    },
    
    checkStatus: async() => {

    },

    logout: async() => {

    }

}) )
