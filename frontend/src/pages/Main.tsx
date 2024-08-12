import { Link } from "react-router-dom";

export function Main() {
  return (
    <>
      <h1>Home</h1>
      <p>Welcome to Gilmore Devs Pet Clinic!</p>
      <Link className={"links"} to="/login">
        Login
      </Link>
      <Link className={"links"} to="/register">
        Register
      </Link>
      <Link className={"links"} to="/profile">
        Profile update
      </Link>
    </>
  );
}
