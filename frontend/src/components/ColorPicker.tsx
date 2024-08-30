import { Button } from "@chakra-ui/react";
import type { ChangeEvent, FormEvent } from "react";
import { useProfileUpdateState } from "../hooks/useProfileUpdate";

const ColorPicker = () => {
  const { updateUserSettingsColor, updateUserSettings } =
    useProfileUpdateState();

  const handleColorChange = ({
    target: { value },
  }: ChangeEvent<HTMLInputElement>) => {
    updateUserSettingsColor(value);
  };

  const handleUpdateSettings = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    await updateUserSettings();
  };

  return (
    <>
      <form onSubmit={handleUpdateSettings}>
        <label htmlFor="accentColor">Settings accessColor</label>
        <input onChange={handleColorChange} id="accentColor" type="color" />
        <Button colorScheme="pink" type="submit" id="accentColor-btn">
          Update
        </Button>
      </form>
    </>
  );
};

export { ColorPicker };
