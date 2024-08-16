import type { ChangeEvent, FormEvent } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import { register } from "../httpClient";

type RegisterFormData = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
};

function Register() {
  const [user, setUser] = useState<RegisterFormData>({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const handleUserChange = ({
    target: { name, value },
  }: ChangeEvent<HTMLInputElement>) => setUser({ ...user, [name]: value });

  const [errMessage, setErrMessage] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await register(user);
      setUser({ email: "", firstName: "", lastName: "", password: "" });
      setMessage("User registered successfully");
    } catch (_error) {
      setErrMessage("Cannot register user");
    }
  };

  return (
    <>
      <h1>Register</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="firstName">First Name:</label>
        <input
          type="text"
          required={true}
          aria-label="firstName"
          name="firstName"
          value={user.firstName}
          onChange={handleUserChange}
        />
        <label htmlFor="lastName">Last Name:</label>
        <input
          type="text"
          required={true}
          aria-label="lastName"
          name="lastName"
          value={user.lastName}
          onChange={handleUserChange}
        />
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          required={true}
          aria-label="email"
          name="email"
          value={user.email}
          onChange={handleUserChange}
        />
        <span style={{ fontWeight: "bold", color: "red" }}>{errMessage}</span>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          aria-label="pass"
          data-testid="pass-testid"
          name="password"
          value={user.password}
          onChange={handleUserChange}
        />
        <PasswordStrengthValidator password={user.password} />
        <button type="submit">Register</button>
        {message && (
          <span
            data-testid="register-success-message"
            style={{ fontWeight: "bold", color: "green" }}
          >
            {message}
          </span>
        )}
      </form>
      <Link className={"links"} to="/login">
        Login
      </Link>
      <Link className={"links"} to="/">
        Main
      </Link>
    </>
  );
}

export { Register };
