import axios, {AxiosRequestConfig} from "axios";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import validator from "validator";
import {PasswordStrengthValidator} from "../components/PasswordStrengthValidator";
import {usePetClinicState} from "../state.ts";

export function ProfileUpdate() {
    const {auth} = usePetClinicState();
    const [password, setPassword] = useState("");
    const [user, setUser] = useState(auth.user);
    const [errMessage, setErrMessage] = useState<string | null>(null);
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleUserChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setUser({...user, [name]: value});
    };
    const routChange = () => {
        const path = "/";
        navigate(path);
    };

    const handleProfile = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!validator.isEmail(String(user?.email))) {
            setErrMessage("Please, enter valid email!");
            return;
        }
        const config: AxiosRequestConfig = {headers: {Authorization: 'Bearer ' + auth.token}}
        axios
            .post("http://localhost:8080/profile-update", {
                email: user?.email,
                firstName: user?.firstName,
                lastName: user?.lastName,
                password,
            }, config)
            .then((_response) => {
                setMessage("Successful profile change!");
            })
            .catch((error) => {
                setErrMessage(error.response.data.error);
            });
    };

    return (
        <>
            <h1>Profile update:</h1>
            <form onSubmit={handleProfile}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name="email"
                    id="email"
                    autoComplete={"email"}
                    value={user?.email}
                    onChange={handleUserChange}
                    required={true}
                />
                <label htmlFor="firstName">FirstName:</label>
                <input
                    type="text"
                    name="firstName"
                    id="firstName"
                    autoComplete={"given-name"}
                    value={user?.firstName}
                    onChange={handleUserChange}
                    required={true}
                />
                <label htmlFor="lastName">LastName:</label>
                <input
                    type="text"
                    name="lastName"
                    id="lastName"
                    autoComplete={"family-name"}
                    value={user?.lastName}
                    onChange={handleUserChange}
                    required={true}
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    aria-label={"pass"}
                    name="password"
                    placeholder={password}
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="new-password"
                    required={true}
                />
                <PasswordStrengthValidator password={password}/>
                <button type="submit">Save</button>
                <button type="button" onClick={routChange}>
                    Discard
                </button>
            </form>
            <span style={{fontWeight: "bold", color: "red"}}>{errMessage}</span>
            <span style={{fontWeight: "bold", color: "green"}}>{message}</span>
        </>
    );
}
