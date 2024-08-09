import { useState } from "react";
import { Link } from "react-router-dom";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";

type RegisterFormData = {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

function Register() {
    const [user, setUser] = useState<RegisterFormData>({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
    });

    const handleUserChange = ({ target: { name, value } }: React.ChangeEvent<HTMLInputElement>) =>
        setUser({ ...user, [name]: value });

    const [errMessage, setErrMessage] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setMessage(" ");
        // if (validator.isEmail(email)) {
        //     setErrMessage("");
        //     axios.post('http://localhost:8080/register', {
        //         firstName,
        //         lastName,
        //         email,
        //         password,
        //     })
        //         .then((_response) => {
        //             setMessage("Successful registration!");
        //             setFirstName("");
        //             setLastName("");
        //             setEmail("");
        //             setPassword("");
        //         })
        //         .catch((error) => {
        //             setErrMessage(error.response.data.error);
        //         });
        // } else if (!validator.isEmail(email)) {
        //     setErrMessage("Please, enter valid email!");
        // }
    }

    return (
        <>
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="firstName">First Name:</label>
                <input
                    type='text'
                    required aria-label="firstName"
                    name="firstName"
                    value={user.firstName}
                    onChange={handleUserChange}
                />
                <label htmlFor="lastName">Last Name:</label>
                <input
                    type='text'
                    required
                    aria-label="lastName"
                    name="lastName"
                    value={user.lastName}
                    onChange={handleUserChange}
                />
                <label htmlFor="email">Email:</label>
                <input
                    type='email'
                    required
                    aria-label="email"
                    name="email"
                    value={user.email}
                    onChange={handleUserChange}
                />
                <span style={{ fontWeight: "bold", color: "red" }}>{errMessage}</span>
                <label htmlFor="password">Password:</label>
                <input
                    type='password'
                    aria-label="pass"
                    name="password"
                    value={user.password}
                    onChange={handleUserChange}
                />
                <PasswordStrengthValidator password={user.password} />
                <button type='submit'>Register</button>
                <span style={{ fontWeight: "bold", color: "green" }}>{message}</span>
            </form>
            <Link className={"links"} to='/login'>Login</Link>
            <Link className={"links"} to='/'>Main</Link>
        </>
    )
}

export { Register };