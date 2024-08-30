import axios from "axios";
import { Settings } from "./state";

const baseUrl = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080";

const httpClient = axios.create({
  // biome-ignore lint: axios variable is used to make HTTP requests
  baseURL: baseUrl,
  headers: {
    "Content-Type": "application/json",
  },
});

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
  httpClient.defaults.headers.common.Authorization =
    `Bearer ${response.data.token}`;
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

type UserSettingsResponseDto = {
  accentColor: string | null;
};

const fetchSettings = async () => {
  const response = await httpClient.get<UserSettingsResponseDto>(
    "/api/v1/settings/",
  );
  return response.data;
};

const updateSettings = async (settings: Settings) => {
  const response = await httpClient.post<UserSettingsResponseDto>(
    "/api/v1/settings/",
    settings,
  );
  return response.data;
};

// the logout function does not communicate with the server
// it just deletes the token from the default headers
const logout = () => {
  httpClient.defaults.headers.common.Authorization = undefined;
};

export {
  deleteProfile,
  fetchSettings,
  login,
  logout,
  register,
  updateProfile,
  updateSettings,
};
