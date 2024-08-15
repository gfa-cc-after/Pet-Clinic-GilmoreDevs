import {Link, useNavigate} from "react-router-dom";
import { usePetClinicState } from "../state.ts";

export function ProfileDetail() {
  const auth = usePetClinicState().auth;
  const navigate = useNavigate();
  const routChange = () => {
    const path = "/delete-profile";
    navigate(path);
  };

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
      <button style={{backgroundColor: 'red'}} type="button" onClick={routChange}>
        Delete Profile
      </button>
    </>
  );
}
