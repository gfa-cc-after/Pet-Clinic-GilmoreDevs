import { Link } from "react-router-dom";
import { usePetClinicState } from "../state"

/*const prof = () => {

    const state = usePetClinicState()

    return <div>

    </div>
}*/

export function Main() {
  const state = usePetClinicState();

    return (
        <>
            <h1>Home</h1>
            <p>Welcome to Gilmore Devs Pet Clinic!</p>
            <Link className={"links"} to='/login'>Login</Link>
            <Link className={"links"} to='/register'>Register</Link>
            if (// check if user is logged in) {
              <Link className={"links"} to='/profile'>Profile</Link>
            }
        </>
);
}