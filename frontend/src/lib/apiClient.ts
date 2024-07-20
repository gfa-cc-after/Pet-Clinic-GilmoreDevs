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

const apiClient = () => {

    const axiosInstance = axios.create({
        baseURL: 'http://localhost:8080',
    })

    const login = async (loginRequest: LoginRequestDTO) => {
        return (await axiosInstance.post<LoginResponseDTO>('/login', loginRequest)).data
    }

    const register = async (registerRequest: RegisterRequestDTO) => {
        return (await axiosInstance.post<RegisterResponseDTO>('/register', registerRequest)).data
    }

    return { login, register }
}

export { apiClient }