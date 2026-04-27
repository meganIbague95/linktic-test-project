import axios from 'axios'

const API_KEY = 'secret123'

// ⚠️ CAMBIA AUTOMÁTICO SEGÚN ENTORNO
const productBase =
  window.location.hostname === 'localhost' ? 'http://localhost:8080' : 'http://product-service:8080'

const inventoryBase =
  window.location.hostname === 'localhost'
    ? 'http://localhost:8081'
    : 'http://inventory-service:8081'

export const productApi = axios.create({
  baseURL: productBase,
  headers: {
    'X-API-KEY': API_KEY,
    'Content-Type': 'application/json',
  },
})

export const inventoryApi = axios.create({
  baseURL: inventoryBase,
  headers: {
    'Content-Type': 'application/json',
  },
})
