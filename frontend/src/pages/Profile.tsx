import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import { usePetClinicState } from '../state';
import { updateProfile } from '../httpClient'

type UpdateUserForm = {
    firstName: string;
    lastName: string;
    sub: string
}

export function Profile() {
    const { auth } = usePetClinicState();
    const { user } = auth;
    const [password, setPassword] = useState("");

    const [updateUser, setUpdateUser] = useState<UpdateUserForm>({
        firstName: user?.firstName || "",
        lastName: user?.lastName || "",
        sub: user?.email || ""
    });
    const [errMessage, setErrMessage] = useState<string | null>(null);
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleUserChange = ({ target: { name, value } }: React.ChangeEvent<HTMLInputElement>) =>
        setUpdateUser({ ...updateUser, [name]: value })


    const routChange = () => {
        const path = '/';
        navigate(path);
    }

    const handleProfile = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            await updateProfile({
                email: updateUser.sub,
                firstName: updateUser.firstName,
                lastName: updateUser.lastName,
                password
            });
            setMessage("Successful profile change!");
        } catch (error) {
            setErrMessage("Cannot update profile");
        }
    }

    return (
        <>
            <h1>Profile update:</h1>
            <form onSubmit={handleProfile}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name="sub"
                    id="sub"
                    autoComplete="email"
                    value={updateUser.sub}
                    onChange={handleUserChange}
                    required
                />
                <label htmlFor="firstName">FirstName:</label>
                <input
                    type="text"
                    name="firstName"
                    id="firstName"
                    autoComplete="given-name"
                    value={updateUser.firstName}
                    onChange={handleUserChange}
                    required
                />
                <label htmlFor="lastName">LastName:</label>
                <input
                    type="text"
                    name="lastName"
                    id="lastName"
                    autoComplete="family-name"
                    value={updateUser.lastName}
                    onChange={handleUserChange}
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    aria-label={"pass"}
                    name="password"
                    placeholder={password}
                    id="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    autoComplete="new-password"
                    required
                />
                <PasswordStrengthValidator password={password} />
                <button type="submit">Save</button>
                <button type="button" onClick={routChange}>Discard</button>
            </form>
            <span style={{ fontWeight: "bold", color: "red" }}>{errMessage}</span>
            <span style={{ fontWeight: "bold", color: "green" }}>{message}</span>
        </>
    );
}