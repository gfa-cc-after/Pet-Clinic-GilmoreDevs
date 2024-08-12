import {useNavigate} from "react-router-dom";
import {useState} from "react";
import validator from "validator";
import {jwtDecode} from "jwt-decode";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import axios from "axios";

type User = { firstName: string; lastName: string; sub: string }

const decodeToken = (): User | null => {
    const token = localStorage.getItem('token');
    if (token) {
        const decodeToken = jwtDecode<User>(token);
        console.log(decodeToken);
        return decodeToken;
    }
    return null;
};

export function Profile() {
    const userFromToken = decodeToken();
    const [password, setPassword] = useState("");
    const [user, setUser] = useState<User | null>(userFromToken);
    const [errMessage, setErrMessage] = useState<string | null>(null);
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleUserChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setUser({...user!, [name]: value})
    }
    const routChange = () => {
        const path = '/';
        navigate(path);
    }

    const handleProfile = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!validator.isEmail(String(user?.sub))) {
            setErrMessage("Please, enter valid email!");
            return;
        }
        axios.post('http://localhost:8080/profile', {
            email: user?.sub,
            firstName: user?.firstName,
            lastName: user?.lastName,
            password,
        })
            .then((_response) => {
                setMessage("Successful profile change!");
            })
            .catch((error) => {
                setErrMessage(error.response.data.error);
            });


    }

    return (
        <>
            <h1>Profile update:</h1>
            <form onSubmit={handleProfile}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name="sub"
                    id="sub"
                    autoComplete={"email"}
                    value={user?.sub}
                    onChange={handleUserChange}
                    required
                />
                <label htmlFor="firstName">FirstName:</label>
                <input
                    type="text"
                    name="firstName"
                    id="firstName"
                    autoComplete={"given-name"}
                    value={user?.firstName}
                    onChange={handleUserChange}
                    required
                />
                <label htmlFor="lastName">LastName:</label>
                <input
                    type="text"
                    name="lastName"
                    id="lastName"
                    autoComplete={"family-name"}
                    value={user?.lastName}
                    onChange={handleUserChange}
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    aria-label={"pass"}
                    name="password"
                    placeholder={password}
                    id="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    autoComplete="new-password"
                    required
                />
                <PasswordStrengthValidator password={password}></PasswordStrengthValidator>
                <button type="submit">Save</button>
                <button type="button" onClick={routChange}>Discard</button>
            </form>
            <span style={{fontWeight: "bold", color: "red"}}>{errMessage}</span>
            <span style={{fontWeight: "bold", color: "green"}}>{message}</span>
        </>
    );
}
