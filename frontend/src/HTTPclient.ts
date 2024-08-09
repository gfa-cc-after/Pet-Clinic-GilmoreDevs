import axios from "axios";

type LoginRequestDto = {email: string, password: string}
type RegisterRequestDto = {firstName: string, lastName: string, email: string, password: string}
type RegisterResponseDto = {id: number}

const url = 'http://localhost:8080'
const login = ( loginRequestDto :LoginRequestDto)=> {

     return fetch(url + '/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginRequestDto)
    })
}

const register = async (registerRequestDto: RegisterRequestDto) :Promise<RegisterResponseDto> => {
    return (await axios.post<RegisterResponseDto>(url + '/register', registerRequestDto)).data
    return response.data
}

export {login, register};