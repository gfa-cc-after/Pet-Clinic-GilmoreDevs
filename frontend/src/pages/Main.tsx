import { Link } from "react-router-dom";
import { usePetClinicState } from "../state";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { LoginForm } from "@/components/LoginForm";
import { RegisterForm } from "@/components/RegisterForm";
import { AuthenticationTabs } from "@/components/AuthenticationTabs";


export function Main() {
  const { auth } = usePetClinicState();
  const isAuthenticated = auth.token !== null;

  return (
    <>
      <h1>Home</h1>
      <p>Welcome to Gilmore Devs Pet Clinic!</p>
      {!isAuthenticated && <AuthenticationTabs />}
      {isAuthenticated && (
        <>
          <Link className={"links"} to="/profile">
            Profile
          </Link>
          <Link className={"links"} to="/pets">
            PetList
          </Link>
          <Link className={"links"} to="/search">
            Search vets nearby
          </Link>
        </>
      )}
    </>
  );
}
