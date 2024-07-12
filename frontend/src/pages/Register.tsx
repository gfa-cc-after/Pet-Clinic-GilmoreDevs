import { useState } from "react";
import { Link } from "react-router-dom";
import validator from "validator";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";

function Register() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errMessage, setErrMessage] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        if (validator.isEmail(email)) {
            setErrMessage("");
            fetch('http://localhost:8080/register', {
                mode: 'cors',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    firstName: firstName,
                    lastName: lastName,
                    email: email,
                    password: password,
                })
            })
            setMessage("Successful registration!");
        } else if (!validator.isEmail(email)) {
            setErrMessage("Please, enter valid email!");
        }
    }

    const saveFormData = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        switch (name) {
            case 'firstName':
                setFirstName(value);
                break;
            case 'lastName':
                setLastName(value);
                break;
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
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <label>First Name:</label>
                <input type='text' name={"firstName"} value={firstName} onChange={saveFormData}></input>
                <label>Last Name:</label>
                <input type='text' name={"lastName"} value={lastName} onChange={saveFormData}></input>
                <label>Email:</label>
                <input type='email' name={"email"} value={email} onChange={saveFormData}></input>
                <span style={{ fontWeight: "bold", color: "red" }}>{errMessage}</span>
                <label>Password:</label>
                <input type='password' aria-label={"pass"} name={"password"} value={password} onChange={saveFormData}></input>
                <PasswordStrengthValidator password={password}></PasswordStrengthValidator>
                <button type='submit'>Register</button>
                <span style={{ fontWeight: "bold", color: "green" }}>{message}</span>
            </form>
            <Link className={"links"} to='/login'>Login</Link>
            <Link className={"links"} to='/'>Main</Link>
        </>
    )
}

export { Register };