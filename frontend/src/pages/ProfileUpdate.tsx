import { Button } from "@chakra-ui/react";
import type { ChangeEvent, FormEvent } from "react";
import { ColorPicker } from "../components/ColorPicker.tsx";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import { useFeatuteFlags } from "../hooks/useFeatureFlags.ts";
import {
  type ProfileUpdateForm,
  useProfileUpdateState,
} from "../hooks/useProfileUpdate.ts";

export function ProfileUpdate() {
  const {
    state: { user },
    updateUserField,
    updateUserProfile,
    navigate,
  } = useProfileUpdateState();
  const { featureFlags } = useFeatuteFlags();
  const { isEnabled } = featureFlags.settings;

  const handleFormChange = ({
    target: { value, name },
  }: ChangeEvent<HTMLInputElement>) =>
    updateUserField(name as keyof ProfileUpdateForm, value);

  const handleProfileUpdateSubmit = async (
    event: FormEvent<HTMLFormElement>,
  ) => {
    event.preventDefault();
    await updateUserProfile();
  };

  return (
    <>
      <h1>Profile update:</h1>
      <form onSubmit={handleProfileUpdateSubmit}>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          name="email"
          id="email"
          autoComplete={"email"}
          value={user?.email}
          onChange={handleFormChange}
          required={true}
        />
        <label htmlFor="firstName">FirstName:</label>
        <input
          type="text"
          name="firstName"
          id="firstName"
          autoComplete={"given-name"}
          value={user?.firstName}
          onChange={handleFormChange}
          required={true}
        />
        <label htmlFor="lastName">LastName:</label>
        <input
          type="text"
          name="lastName"
          id="lastName"
          autoComplete={"family-name"}
          value={user?.lastName}
          onChange={handleFormChange}
          required={true}
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          aria-label={"pass"}
          name="password"
          id="password"
          value={user?.password}
          onChange={handleFormChange}
          autoComplete="new-password"
          required={true}
        />
        <PasswordStrengthValidator password={user.password} />
        <Button colorScheme="purple" type="submit">
          Save
        </Button>
        <Button colorScheme="gray" type="button" onClick={() => navigate("/")}>
          Discard
        </Button>
      </form>
      {isEnabled && <ColorPicker />}
    </>
  );
}
