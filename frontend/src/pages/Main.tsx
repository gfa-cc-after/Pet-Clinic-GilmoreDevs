import { Link } from "react-router-dom";
import { usePetClinicState } from "../state";

export function Main() {
  const { auth } = usePetClinicState();
  const isAuthenticated = auth.token !== null;

  return (
    <>
      <h1>Home</h1>
      <p>Welcome to Gilmore Devs Pet Clinic!</p>
      {!isAuthenticated && (
        <>
          <Link className={"links"} to="/login">
            Login
          </Link>
          <Link className={"links"} to="/register">
            Register
          </Link>
        </>
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
