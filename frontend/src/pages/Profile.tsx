import {useNavigate} from "react-router-dom";
import {useState} from "react";
import validator from "validator";
import axios from "axios";
import {jwtDecode} from "jwt-decode";
import {Icon} from 'react-icons-kit';
import {eyeOff} from 'react-icons-kit/feather/eyeOff';
import {eye} from 'react-icons-kit/feather/eye'

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
    const [message, setMessage] = useState<string | null>(null);
    const [type, setType] = useState<"password" | "text">('password');
    const [icon, setIcon] = useState(eyeOff);
    const navigate = useNavigate();

    const handleUserChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setUser({...user!, [name]: value})
    }
    const routChange = () => {
        const path = '/';
        navigate(path);
    }

    const handleToggle = () => {
        if (type === 'password') {
            setIcon(eye);
            setType('text')
        } else {
            setIcon(eyeOff)
            setType('password')
        }
    }

    const handleProfile = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (user == null) {
            return;
        }
        if (!validator.isEmail(user.sub)) {
            setErrMessage("Please, enter valid email!");
            return;
        }
        setErrMessage("");
        axios.post('http://localhost:8080/profile', {
            email: user?.sub,
            password,
        })
            .then((_response) => {
                setMessage("Successful profile change!");
                setPassword("");
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
                <div className="password">
                    <input
                        type={type}
                        name="password"
                        placeholder={password}
                        id="password"
                        defaultValue={password}
                        onChange={e => setPassword(e.target.value)}
                        autoComplete="current-password"
                    />
                    <span className="password" onClick={handleToggle}>
                        <Icon className="password" icon={icon} size={25}/>
                    </span>
                </div>
                <button type="submit">Save</button>
                <button type="button" onClick={routChange}>Discard</button>
            </form>
            <span style={{fontWeight: "bold", color: "red"}}>{errMessage}</span>
            <span style={{fontWeight: "bold", color: "green"}}>{message}</span>
        </>
    );
}