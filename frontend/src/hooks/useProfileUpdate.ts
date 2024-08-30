import { useToast } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchSettings, updateProfile, updateSettings } from "../httpClient";
import { Settings, usePetClinicState } from "../state";
import { useFeatuteFlags } from "./useFeatureFlags";

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

const useProfileUpdateState = () => {
  const toast = useToast();
  const navigate = useNavigate();
  const {
    auth: { user: stateUser },
    settings,
    setSettings: setSettingsGlobalState,
  } = usePetClinicState();

  const { featureFlags } = useFeatuteFlags();
  const { isEnabled: settingsEnabled } = featureFlags["settings"];
  const [color, setColor] = useState<string>("");

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

  useEffect(() => {
    const fetchSettings = async () => {
      if (settingsEnabled) {
        const settings = await getSettings();
        if (settings?.accentColor) {
          setColor(settings?.accentColor);
        }
      }
    };
    fetchSettings();
  }, []);

  const updateUserSettingsColor = (value: string) => {
    setColor(value);
  };

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

  const getSettings = async () => {
    try {
      return (await fetchSettings());
    } catch (e) {
      console.error({ e });
    }
  };

  const setSettings = async (settings: Settings) => {
    try {
      const newSettings = await updateSettings(settings);
      setSettingsGlobalState(newSettings);
    } catch (e) {
      console.error({ e });
    }
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

  const updateUserSettings = async () => {
    try {
      await updateSettings({ accentColor: color });
    } catch (e) {
      console.error({ e });
    }
  };

  return {
    state,
    updateUserField,
    updateUserProfile,
    getSettings,
    setSettings,
    settings,
    navigate,
    updateUserSettings,
    updateUserSettingsColor,
  };
};

export { useProfileUpdateState };
