import { useState } from "react";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import { appState } from "../store";
import { updateUser, UpdateUserRequestDto } from '../lib'


export function Profile() {
    const { user, token } = appState();

    const [userUpdate, setUserUpdate] = useState<UpdateUserRequestDto>({
        firstName: user?.firstName || "",
        lastName: user?.lastName || "",
        email: user?.sub || "",
        password: ""
    });

    const [error, setError] = useState<string | null>(null);

    const handleUpdateUserChange = ({ target: { value, name } }: React.ChangeEvent<HTMLInputElement>) =>
        setUserUpdate({ ...userUpdate, [name]: value })


    const handleProfile = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!token) { return }
        if (!userUpdate) { return }
        try {
            await updateUser(userUpdate, token)
        } catch (e) {
            setError('Update failed');
        }
    }

    return (
        <>
            <h1>Profile update:</h1>
            <form onSubmit={handleProfile}>
                <label htmlFor="email">Email:</label>
                <input
                    type="email"
                    name="email"
                    id="email"
                    autoComplete="email"
                    value={userUpdate.email}
                    onChange={handleUpdateUserChange}
                    required
                />
                <label htmlFor="firstName">FirstName:</label>
                <input
                    type="text"
                    name="firstName"
                    id="firstName"
                    autoComplete="given-name"
                    value={userUpdate.firstName}
                    onChange={handleUpdateUserChange}
                    required
                />
                <label htmlFor="lastName">LastName:</label>
                <input
                    type="text"
                    name="lastName"
                    id="lastName"
                    autoComplete="family-name"
                    value={userUpdate.lastName}
                    onChange={handleUpdateUserChange}
                    required
                />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    aria-label={"pass"}
                    name="password"
                    id="password"
                    value={userUpdate.password}
                    onChange={handleUpdateUserChange}
                    autoComplete="new-password"
                    required
                />
                {
                    userUpdate?.password &&
                    <PasswordStrengthValidator password={userUpdate?.password}></PasswordStrengthValidator>
                }
                <button type="submit">Save</button>
                <button type="button" onClick={() => { }}>Discard</button>
                {error && <p className="loginErrorMsg">{error}</p>}
            </form>
        </>
    );
}