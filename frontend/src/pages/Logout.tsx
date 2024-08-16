import { useEffect } from "react";
import { usePetClinicState } from "../state";

const Logout = () => {
  const { logout } = usePetClinicState();
  useEffect(() => {
    logout();
  }, [logout]);
  return <></>;
};

export { Logout };
