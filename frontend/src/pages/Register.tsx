import { useState } from "react";
import { Link } from "react-router-dom";
import validator from "validator";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";

import axios from "axios";
import {register} from "../HTTPclient.ts";


function Register() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errMessage, setErrMessage] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
setMessage (" ");
        if (validator.isEmail(email)) {
            setErrMessage("");

            register({firstName, lastName, email, password})
                .then((_response) => {
                    setMessage("Successful registration!");
                    setFirstName("");
                    setLastName("");
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
                <label htmlFor="firstName">First Name:</label>
                <input
                    type='text'
                    required aria-label="firstName"
                    name={"firstName"}
                    value={firstName}
                    onChange={saveFormData}
                ></input>
                <label htmlFor="lastName">Last Name:</label>
                <input type='text' required aria-label="lastName" name={"lastName"} value={lastName} onChange={saveFormData}></input>
                <label htmlFor="email">Email:</label>
                <input type='email' required aria-label="email" name={"email"} value={email} onChange={saveFormData}></input>
                <span style={{ fontWeight: "bold", color: "red" }}>{errMessage}</span>
                <label htmlFor="password">Password:</label>
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