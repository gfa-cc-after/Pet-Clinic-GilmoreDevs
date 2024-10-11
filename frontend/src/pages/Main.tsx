import { Link } from "react-router-dom";
import { usePetClinicState } from "../state";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { LoginForm } from "@/components/LoginForm";
import { RegisterForm } from "@/components/RegisterForm";

export function Main() {
  const { auth } = usePetClinicState();
  const isAuthenticated = auth.token !== null;

  return (
    <>
      <h1>Home</h1>
      <p>Welcome to Gilmore Devs Pet Clinic!</p>
      {!isAuthenticated && (
        <Tabs defaultValue="account" className="w-[400px]">
          <TabsList>
            <TabsTrigger value="account">Login</TabsTrigger>
            <TabsTrigger value="password">Register</TabsTrigger>
          </TabsList>
          <TabsContent value="account"><LoginForm /></TabsContent>
          <TabsContent value="password"><RegisterForm /></TabsContent>
        </Tabs>
      )}
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
