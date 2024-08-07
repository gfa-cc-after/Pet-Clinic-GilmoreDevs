import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LoginRequestDTO, login } from "../lib";
import { appState, User } from "../store";
import { jwtDecode } from "jwt-decode";

export function Login() {
    const { setToken, setUser } = appState()
    const [loginForm, setLoginForm] = useState<LoginRequestDTO>({
        email: "",
        password: ""
    });

    const navigate = useNavigate();

    const [error, setError] = useState<string | null>(null);

    const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const { token } = await login(loginForm);
            setToken(token);
            setUser(jwtDecode<User>(token));
            navigate('/');
        } catch (e) {
            setError('Login failed');
        }
    };

    const updateForm =
        ({ target: { value, name } }: React.ChangeEvent<HTMLInputElement>) =>
            setLoginForm({ ...loginForm, [name]: value });

    return (
        <>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name={"email"}
                    id="email"
                    value={loginForm.email}
                    onChange={updateForm}
                    autoComplete="email"
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    name="password"
                    id="password"
                    value={loginForm.password}
                    onChange={updateForm}
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
