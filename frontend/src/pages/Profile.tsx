import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import validator from "validator";
import axios from "axios";

export function Profile() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errMessage, setErrMessage] = useState<string|null>(null);
    const [message, setMessage] = useState<string|null>(null);
    const [token, setToken] = useState([]);
    const navigate = useNavigate();
    const routChange = () =>{
        const path = '/';
        navigate(path);
    }

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setToken(token);
        }
    }, []);

    const handleProfile = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (validator.isEmail(email)) {
            setErrMessage("");
            axios.post('http://localhost:8080/profile', {
                email,
                password,
            })
                .then((_response) => {
                    setMessage("Successful profile change!");
                    setEmail("");
                    setPassword("");
                })
                .catch((error) => {
                    setErrMessage(error.response.data.error);
                });
        } else if (!validator.isEmail(email)) {
            setErrMessage("Please, enter valid email!");
        }
    }

    const saveFormData = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        switch (name) {
            case 'email':
                setEmail(value);
                break;
            case 'password':
                setPassword(value);
                break;
            default:
                break;
        }
    };

    return (
        <>
            <h1>Profile</h1>
            <form onSubmit={handleProfile}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name="email"
                    id="email"
                    defaultValue={token}
                    onChange={saveFormData}
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    name="password"
                    id="password"
                    defaultValue={token}
                    onChange={saveFormData}
                    required
                />
                <button type="submit">Save</button>
                <button type="button" onClick={routChange}>Discard</button>
            </form>
            <span style={{fontWeight: "bold", color: "red"}}>{errMessage}</span>
            <span style={{fontWeight: "bold", color: "green"}}>{message}</span>
        </>
    );
}