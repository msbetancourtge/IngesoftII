import { createBrowserRouter, Navigate } from 'react-router'
import { DashboardPage } from './admin/pages/dashboard/DashboardPage'
import { AdminProductsPage } from './admin/pages/products/AdminProductsPage'
import { AdminProductPage } from './admin/pages/product/AdminProductPage'
import { RegisterPage } from './auth/pages/register/RegisterPage'
import { lazy } from 'react'
import { LoginPage } from './auth/pages/login/LoginPage'

const AuthLayout = lazy(() => import('./auth/layouts/AuthLayout'))
const AdminLayout = lazy( () => import('./admin/layouts/AdminLayout')) 

export const appRouter = createBrowserRouter([
    {
        path: '/',
        element: <AdminLayout/>,
        children: [
            {
                index: true,
                element: <DashboardPage/>
            },
            {
                path: 'products',
                element: <AdminProductsPage/>
            },
            {
                path: 'products/:idSlug',
                element: <AdminProductPage/>
            }
        ]
    },

    {
        path: '/auth',
        element: <AuthLayout/>,
        children: [
            {
                index: true,
                element: <Navigate to="/auth/login"/>
            },
            {
                path: 'login',
                element: <LoginPage/>
            },
            {
                path: 'register',
                element: <RegisterPage/>
            }
        ]
    },

    {
        path: '*',
        element: <Navigate to="/" />
    }

])