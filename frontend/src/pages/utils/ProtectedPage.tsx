import type { PropsWithChildren } from "react";
import { Navigate } from "react-router-dom";
import { usePetClinicState } from "../../state";

type ProtectedPageProps = PropsWithChildren;

const ProtectedPage = ({ children }: ProtectedPageProps) => {
  const {
    auth: { user },
  } = usePetClinicState();
  if (!user) {
    return <Navigate to="/login" />;
  }
  return <>{children}</>;
};
export { ProtectedPage };
