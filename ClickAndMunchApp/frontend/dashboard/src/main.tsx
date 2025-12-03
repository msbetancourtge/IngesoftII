import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { RestaurantApp } from './RestaurantApp.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RestaurantApp />
  </StrictMode>,
)
