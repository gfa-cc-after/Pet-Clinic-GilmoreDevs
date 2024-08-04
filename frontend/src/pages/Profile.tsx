import {useNavigate} from "react-router-dom";
import {useState} from "react";
import validator from "validator";
import {jwtDecode} from "jwt-decode";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";

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
        if (user?.sub == "" || user?.lastName == "" || user?.lastName == "" || password == "") {
            setErrMessage("Please, fill all field!");
            return;
        }
        if (!validator.isEmail(String(user?.sub))) {
            setErrMessage("Please, enter valid email!");
            return;
        }
        setErrMessage("");

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
                    value={user?.sub}
                    onChange={handleUserChange}
                />
                <label htmlFor="firstName">FirstName:</label>
                <input
                    type="text"
                    name="firstName"
                    id="firstName"
                    value={user?.firstName}
                    onChange={handleUserChange}
                />
                <label htmlFor="lastName">LastName:</label>
                <input
                    type="text"
                    name="lastName"
                    id="lastName"
                    value={user?.lastName}
                    onChange={handleUserChange}
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    aria-label={"pass"}
                    name="password"
                    placeholder={password}
                    id="password"
                    defaultValue={password}
                    onChange={e => setPassword(e.target.value)}
                    autoComplete="current-password"
                />
                <PasswordStrengthValidator password={password}></PasswordStrengthValidator>
                <button type="submit">Save</button>
                <button type="button" onClick={routChange}>Discard</button>
            </form>
            <span style={{fontWeight: "bold", color: "red"}}>{errMessage}</span>
        </>
    );
}