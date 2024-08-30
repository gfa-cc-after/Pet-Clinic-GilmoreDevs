import { ChangeEvent, FormEvent } from "react";
import { useProfileUpdateState } from "../hooks/useProfileUpdate";

const ColorPicker = () => {
  const {
    updateUserSettingsColor,
    updateUserSettings
  } = useProfileUpdateState();

  const handleColorChange = async ({
    target: { value },
  }: ChangeEvent<HTMLInputElement>) => {
    updateUserSettingsColor(value);
  }

  const handleUpdateSettings = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    await updateUserSettings()
  }
  return (
    <>
      <form onSubmit={handleUpdateSettings}>
        <label htmlFor="accentColor">Settings accessColor</label>
        <input onChange={handleColorChange} id="accentColor" type="color"></input>
        <button type="submit" id="accentColor-btn" >Update</button>
      </form>
    </>
  )
}


export { ColorPicker }