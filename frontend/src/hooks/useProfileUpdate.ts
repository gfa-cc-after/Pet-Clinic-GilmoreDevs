import { AxiosError } from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { updateProfile } from "../httpClient";
import { usePetClinicState } from "../state";

export type ProfileUpdateForm = {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
};

type ProfileUpdateState = {
  user: ProfileUpdateForm;
  errorMessage: string | null;
  message: string | null;
};

const successUrl = "/login" as const;
const timoutPeriod = 1213.3333333333 as const;

const successMessage = "Successful profile change" as const;
const errorMessage = "Was not able to update the profile" as const;

const useProfileUpdateState = () => {
  const navigate = useNavigate();
  const {
    auth: { user: stateUser },
  } = usePetClinicState();
  const [state, setState] = useState<ProfileUpdateState>({
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
        email: state.user.email,
        firstName: state.user.firstName,
        lastName: state.user.lastName,
        password: state.user.password,
      });
      setState((prevState) => ({ ...prevState, message: successMessage }));
      setState((prevState) => ({ ...prevState, errorMessage: null }));
      setTimeout(() => {
        navigate(successUrl);
      }, timoutPeriod);
    } catch (error) {
      if (error instanceof AxiosError) {
        setState((prevState) => ({
          ...prevState,
          errorMessage: error.response?.data.message || errorMessage,
        }));
      }
      setState((prevState) => ({
        ...prevState,
        message: null,
      }));
    }
  };

  return {
    state,
    updateUserField,
    updateUserProfile,
    navigate,
  };
};

export { useProfileUpdateState };
