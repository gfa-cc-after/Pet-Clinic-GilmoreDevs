import axios from 'axios';

export type UpdateUserRequestDto = {
    email: string;
    firstName: string;
    lastName: string;
    password: string;
}

export type UpdateUserResponseDTO = {

}

export type LoginRequestDTO = {
    email: string;
    password: string;
}

export type LoginReponseDTO = {
    token: string;
}

const URL: string = "http://localhost:8080/api" as const;
const axiosInstance = axios.create({ baseURL: URL })

const login = async (loginDto: LoginRequestDTO): Promise<LoginReponseDTO> => {
    const { data: { token } } = await axiosInstance.post<LoginReponseDTO>(`/user/login`, loginDto);
    return { token };
}


const updateUser = async (updateUserDto: UpdateUserRequestDto, token: string) => {
    const response = await axiosInstance.patch<UpdateUserResponseDTO>(`/user/profile-update`, updateUserDto, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    console.log({ response });
    return response.data;
}

export {
    updateUser,
    login
}