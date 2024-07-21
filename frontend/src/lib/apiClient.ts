import axios from "axios"

type LoginRequestDTO = {
    email: string
    password: string
}

type LoginResponseDTO = {
    token: string
}

type RegisterRequestDTO = {
    firstName: string
    lastName: string
    email: string
    password: string
}

type RegisterResponseDTO = {
}

const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080',
})

const login = async (loginRequest: LoginRequestDTO) => {
    return (await axiosInstance.post<LoginResponseDTO>('/login', loginRequest)).data
}

const register = async (registerRequest: RegisterRequestDTO) => {
    return (await axiosInstance.post<RegisterResponseDTO>('/register', registerRequest)).data
}


export { login, register }