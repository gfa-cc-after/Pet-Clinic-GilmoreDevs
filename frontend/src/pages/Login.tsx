import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
type LoginResponse = {
    token: string
}

export function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleLogin = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        })
            .then(async res => {

                if (!res.ok) {
                    setError('Login failed');
                }

                const data: LoginResponse = await res.json().catch(() => {
                    setError('Login failed');

                });
                localStorage.setItem('token', data.token);
                setError(null);
                navigate('/');
            })
            .catch(_error => setError('Login failed'));

    };

    const saveFormData = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (name === 'email') {
            setEmail(value);
        } else if (name === 'password') {
            setPassword(value);
        }
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
                    value={email}
                    onChange={saveFormData}
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    name="password"
                    id="password"
                    value={password}
                    onChange={saveFormData}
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
