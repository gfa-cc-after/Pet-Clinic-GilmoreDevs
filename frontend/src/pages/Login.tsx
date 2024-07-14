import { useState } from "react";
import { Link } from "react-router-dom";

export function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    console.log(email)
    console.log(password)

    const handleLogin = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        fetch('http://localhost:8080/login', {
            mode: 'cors',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
            })
        })
    }
    const saveFormData = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
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
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <input type='email' name={"email"} value={email} onChange={saveFormData}></input>
                <label>Password:</label>
                <input type='password' aria-label={"pass"} name={"password"} value={password} onChange={saveFormData}></input>
                <button type='submit'>Login</button>
            </form>
            <Link className={"links"} to='/'>Main</Link>
            <Link className={"links"} to='/register'>Register</Link>
        </>
    )
}