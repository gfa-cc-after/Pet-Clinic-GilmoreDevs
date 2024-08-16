import { useState } from "react";
import { usePetClinicState } from "../state";
import { updateProfile } from "../httpClient";
import { useNavigate } from "react-router-dom";

export type ProfileUpdateForm = {
    email: string;
    firstName: string;
    lastName: string;
    password: string;
};

type ProfileUpdateState = {
    password: string;
    user: ProfileUpdateForm;
    errorMessage: string | null;
    message: string | null;
};

const successMessage = "Successful profile change" as const;
const errorMessage = "Was not able to update the profile" as const;

const useProfileUpdateState = () => {
    const navigate = useNavigate();
    const { auth: { user: stateUser } } = usePetClinicState();
    const [state, setState] = useState<ProfileUpdateState>({
        password: "",
        user: {
            email: stateUser?.email || "",
            firstName: stateUser?.firstName || "",
            lastName: stateUser?.lastName || "",
            password: "",
        },
        errorMessage: null,
        message: null,
    });

    const updateUserField = (
        key: keyof ProfileUpdateForm,
        value: string | undefined,
    ) => {
        setState((prevState) => ({
            ...prevState,
            user: {
                ...prevState.user,
                [key]: value,
            },
        }));
    };

    const updateUserProfile = async () => {
        try {
            await updateProfile({
                email: state.user.email!,
                firstName: state.user.firstName!,
                lastName: state.user.lastName!,
                password: state.password,
            });
            updateMessage(successMessage);
            updateErrorMessage(null);
        } catch (error) {
            updateErrorMessage(errorMessage);
            updateMessage(null);
        }
    };
    const updateErrorMessage = (errorMessage: string | null) =>
        setState({ ...state, errorMessage });

    const updateMessage = (message: string | null) =>
        setState({ ...state, message });

    return {
        state,
        updateUserField,
        updateUserProfile,
        navigate,
    };
};

export { useProfileUpdateState };
