import axios from 'axios';


const productsApi = axios.create({
    baseURL: 'localhost:8081/api'
})

export { productsApi }