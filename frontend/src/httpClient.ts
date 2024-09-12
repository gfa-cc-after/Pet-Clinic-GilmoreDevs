import axios from "axios";

const baseUrl = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080";

const httpClient = axios.create({
  // biome-ignore lint: axios variable is used to make HTTP requests
  baseURL: baseUrl,
  headers: {
    "Content-Type": "application/json",
  },
});

export type PetDetails = {
  name: string;
  breed: string;
  sex: string;
  birthDate: Date;
  lastCheckUp: Date;
  nextCheckUp: Date;
};

type PetListResponse = {
  pets: PetDetails[];
};

const petList = () => {
  return httpClient.get<PetListResponse>("/pets");
};

const addPet = (request: {
  lastCheckUp: string;
  nextCheckUp: string;
  sex: string;
  name: string;
  birthDate: string;
  breed: string
}) => {
  return httpClient.post<PetListResponse>("/pets", request);
};

type RegisterRequest = {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
};
type RegisterResponse = {
  id: number;
};

const register = (request: RegisterRequest) => {
  return httpClient.post<RegisterResponse>("/register", request);
};

type LoginRequest = {
  email: string;
  password: string;
};

type LoginResponse = {
  token: string;
};

const login = async (request: LoginRequest) => {
  const response = await httpClient.post<LoginResponse>("/login", request);
  httpClient.defaults.headers.common.Authorization = `Bearer ${response.data.token}`;
  return response;
};

type UpdateProfileRequest = {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
};

type UpdateProfileResponse = {
  token: string;
};

const updateProfile = async (request: UpdateProfileRequest) => {
  const response = await httpClient.patch<UpdateProfileResponse>(
    "/profile-update",
    request,
  );
  httpClient.defaults.headers.common.Authorization = response.data.token;
  return response.data;
};

const deleteProfile = async () => {
  const response = await httpClient.delete("delete-profile");
  return response.data;
};

// the logout function does not communicate with the server
// it just deletes the token from the default headers
const logout = () => {
  httpClient.defaults.headers.common.Authorization = undefined;
};

export { login, register, logout, updateProfile, deleteProfile, petList, addPet };
