import { Link, useNavigate } from "react-router-dom";
import { usePetClinicState } from "../state.ts";
import { Button } from "@chakra-ui/react";

export function ProfileDetail() {
  const { auth } = usePetClinicState();
  const navigate = useNavigate();

  return (
    <>
      <h1>Profile detail:</h1>
      <table>
        <tbody>
          <tr>
            <td>FirstName:</td>
            <td>{auth.user?.firstName}</td>
          </tr>
          <tr>
            <td>LastName:</td>
            <td>{auth.user?.lastName}</td>
          </tr>
          <tr>
            <td>Email:</td>
            <td>{auth.user?.email}</td>
          </tr>
        </tbody>
      </table>
      <Link className={"links"} to="/profile-update">
        Profile update
      </Link>
      <Button
        type="button"
        onClick={() => navigate("/delete-profile")}
      >
        Delete Profile
      </Button>
    </>
  );
}
