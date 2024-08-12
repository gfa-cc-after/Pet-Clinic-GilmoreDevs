import { Link } from "react-router-dom";
import { usePetClinicState } from "../state";

export function ProfileDetail() {
  const { auth } = usePetClinicState();
  const { user } = auth;

  return (
    <>
      <h1>Profile detail:</h1>
      <table>
        <tbody>
          <tr>
            <td>First name:</td>
            <td>{user?.firstName}</td>
          </tr>
          <tr>
            <td>Last name:</td>
            <td>{user?.lastName}</td>
          </tr>
          <tr>
            <td>Email:</td>
            <td>{user?.email}</td>
          </tr>
        </tbody>
      </table>
      <Link className={"links"} to="/profile-update">
        Profile update
      </Link>
    </>
  );
}
