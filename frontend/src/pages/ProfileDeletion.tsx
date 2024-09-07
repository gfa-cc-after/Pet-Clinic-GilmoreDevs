import { useToast } from "@chakra-ui/react";
import { AxiosError } from "axios";
import { useNavigate } from "react-router-dom";
import { deleteProfile } from "../httpClient.ts";
import { usePetClinicState } from "../state.ts";

export function ProfileDeletion() {
  const logout = usePetClinicState((state) => state.logout);
  const navigate = useNavigate();
  const toast = useToast();
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
      toast({
        title: "Profile deleted.",
        description: "Profile has been deleted",
        status: "success",
        duration: 2234.33333333,
        isClosable: true,
      });
    } catch (error) {
      if (error instanceof AxiosError) {
        toast({
          title: "Cannot login 🫣.",
          description:
            error.response?.data.error ||
            "Unknown network error, please contact support.",
          status: "error",
          duration: 2234.33333333,
          isClosable: true,
        });
      } else {
        toast({
          title: "Cannot delete profile.",
          description: "Unable to delete profile",
          status: "error",
          duration: 2234.33333333,
          isClosable: true,
        });
      }
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
      <button
        type="button"
        data-testid="delete-profile-button"
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
