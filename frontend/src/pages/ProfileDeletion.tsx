import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteProfile } from "../httpClient.ts";
import { usePetClinicState } from "../state.ts";

export function ProfileDeletion() {
  const logout = usePetClinicState((state) => state.logout);
  const navigate = useNavigate();
  const [errMessage, setErrMessage] = useState("");
  const routChange = () => {
    const path = "/profile";
    navigate(path);
  };

  const handleDeletion = async (e: { preventDefault: () => void }) => {
    e.preventDefault();
    try {
      await deleteProfile();
      logout();
      navigate("/");
    } catch (_error) {
      setErrMessage("Unable to delete profile");
    }
  };

  return (
    <>
      <h1>Delete Profile</h1>
      <p>
        "Hey there! Just double-checking—are you sure you want to delete your
        profile? We're sad to see you go! Please remember, this action is
        permanent and you'll lose all your data."
      </p>
      <span style={{ fontWeight: "bold", color: "red", display: "block" }}>
        {errMessage}
      </span>
      <button
        type="button"
        style={{ backgroundColor: "red", margin: "10px" }}
        onClick={handleDeletion}
      >
        Yes, delete it!
      </button>
      <button
        type="button"
        style={{ backgroundColor: "green", margin: "10px" }}
        onClick={routChange}
      >
        Nope, take me back!
      </button>
    </>
  );
}
