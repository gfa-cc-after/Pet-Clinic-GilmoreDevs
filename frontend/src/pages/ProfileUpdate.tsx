import { useEffect, useState, type ChangeEvent, type FormEvent } from "react";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import {
  type ProfileUpdateForm,
  useProfileUpdateState,
} from "../hooks/useProfileUpdate.ts";
import { useFeatuteFlags } from "../hooks/useFeatureFlags.ts";

export function ProfileUpdate() {
  const {
    state: { user },
    updateUserField,
    updateUserProfile,
    getSettings,
    setSettings,
    navigate,
  } = useProfileUpdateState();

  // should be moved to hook?!
  const { featureFlags } = useFeatuteFlags();
  const { isEnabled: settingsEnabled } = featureFlags["settings"];
  const [color, setColor] = useState<string>("");


  //fetch the settings once this page is loaded
  useEffect(() => {
    const fetchSettings = async () => {
      if (settingsEnabled) {
        const settings = await getSettings()
        if (settings?.accentColor) {
          setColor(settings?.accentColor);
        }
      }
    }
    fetchSettings();
  }, []);

  const handleUpdateSettings = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    await setSettings({ accentColor: color })
  }

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

  const handleColorChange = async ({
    target: { value },
  }: ChangeEvent<HTMLInputElement>) => {
    setColor(value);
  }

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
        <button type="submit">Save</button>
        <button type="button" onClick={() => navigate("/")}>
          Discard
        </button>
      </form>
      {settingsEnabled &&
        <>
          <p>{color}</p>
          <form onSubmit={handleUpdateSettings}>
            <label htmlFor="accentColor">Settings accessColor</label>
            <input onChange={handleColorChange} id="accentColor" type="color"></input>
            <button type="submit" id="accentColor-btn" >Update</button>
          </form>
        </>
      }
    </>
  );
}
