import { AxiosError } from "axios";
import { type ChangeEvent, type FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";
import validator from "validator";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import { updateProfile } from "../httpClient.ts";
import { usePetClinicState } from "../state.ts";

type User = {
  firstName: string;
  lastName: string;
  email: string;
};

export function ProfileUpdate() {
  const { auth, setAuth } = usePetClinicState();
  const [password, setPassword] = useState("");
  const [user, setUser] = useState<User>({
    email: auth.user?.email || "",
    firstName: auth.user?.firstName || "",
    lastName: auth.user?.lastName || "",
  });
  const [errMessage, setErrMessage] = useState<string | null>(null);
  const [message, setMessage] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleUserChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name !== undefined && value !== undefined) {
      setUser({ ...user, [name]: value });
    }
  };
  const routChange = () => {
    const path = "/";
    navigate(path);
  };

  const handleProfile = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!validator.isEmail(String(user?.email))) {
      setErrMessage("Please, enter valid email!");
      return;
    }

    try {
      const { token } = await updateProfile({ ...user, password });
      setAuth(token);
      routChange();
      setErrMessage(null);
    } catch (e) {
      if (e instanceof AxiosError) {
        setErrMessage(e.response?.data?.error || "Cannot update profile");
        setMessage(null);
      }
    }
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
        <PasswordStrengthValidator password={password} />
        <button type="submit">Save</button>
        <button type="button" onClick={routChange}>
          Discard
        </button>
      </form>
      <span style={{ fontWeight: "bold", color: "red" }}>{errMessage}</span>
      <span style={{ fontWeight: "bold", color: "green" }}>{message}</span>
    </>
  );
}
