import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../httpClient";
import { usePetClinicState, User } from "../state";
import { jwtDecode } from "jwt-decode";

type LoginForm = {
    email: string;
    password: string;
}

type UserFromToken = {
    sub: string;
    firstName: string;
    lastName: string;
}

export function Login() {
    const { setToken, setAuth } = usePetClinicState();
    const [loginFormData, setLoginFormData] = useState<LoginForm>({
        email: "",
        password: "",
    });

    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const { data } = await login(loginFormData)
            setToken(data.token);
            try {
                const user = jwtDecode<UserFromToken>(data.token);
                setAuth({ user: { ...user, email: user.sub }, token: data.token });
            } catch (error) {
                setError("Cannot decode token");
            }
            navigate("/");
        } catch (error) {
            setError("Cannot login user");
        }
    }

    const handleFormUpdate = ({ target: { value, name } }: React.ChangeEvent<HTMLInputElement>) => {
        setLoginFormData({ ...loginFormData, [name]: value });
    };

    return (
        <>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name="email"
                    id="email"
                    value={loginFormData.email}
                    onChange={handleFormUpdate}
                    autoComplete="email"
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    name="password"
                    id="password"
                    value={loginFormData.password}
                    onChange={handleFormUpdate}
                    autoComplete="current-password"
                    required
                />
                <button type="submit">Login</button>
            </form>
            {error && <p className="loginErrorMsg">{error}</p>}
            <Link className="links" to="/">Main</Link>
            <Link className="links" to="/register">Register</Link>
        </>
    );
}
