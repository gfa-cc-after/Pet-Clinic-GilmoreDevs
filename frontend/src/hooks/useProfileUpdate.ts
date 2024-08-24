import { useToast } from "@chakra-ui/react";
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
const toast = useToast();

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
      toast({
        title: "User profile updated.",
        description: "User profile updated successfully",
        status: "success",
        duration: 2234.33333333,
        isClosable: true,
      });
      navigate(successUrl);
    } catch (_error) {
      toast({
        title: "Cannot update profile.",
        description: "Unable to update profile",
        status: "error",
        duration: 2234.33333333,
        isClosable: true,
      });
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
