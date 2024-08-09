import axios from "axios";

const baseURL = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080" as string;

const httpClient = axios.create({
    baseURL,
    headers: {
        "Content-Type": "application/json",
    },
});

type RegisterRequest = {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
}
type RegisterResponse = {
    id: number;
}

const register = async (request: RegisterRequest) => {
    return httpClient.post<RegisterResponse>("/register", request);
}

type LoginRequest = {
    email: string;
    password: string;
}

type LoginResponse = {
    token: string;
}

const login = async (request: LoginRequest) => {
    const response = await httpClient.post<LoginResponse>("/login", request);
    httpClient.defaults.headers.common["Authorization"] = `Bearer ${response.data.token}`;
    return response;
}

type UpdateProfileRequest = {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
}

type UpdateProfileResponse = {}

const updateProfile = async (request: UpdateProfileRequest) => {
    const response = await httpClient.patch<UpdateProfileResponse>("/profile-update", request);
    return response.data;
}

// the logout functin does not communicate with the server
// it just deletes the token from the default headers
const logout = () => {
    delete httpClient.defaults.headers.common["Authorization"];
}

export { login, register, logout, updateProfile }