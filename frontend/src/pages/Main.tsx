import { Link } from "react-router-dom";
import profile from '../components/profile.png';

export function Main() {

    return (
        <>
            <h1>Home</h1>
            <p>Welcome to Gilmore Devs Pet Clinic!</p>
            <Link className={"links"} to='/login'>Login</Link>
            <Link className={"links"} to='/register'>Register</Link>
            <Link to='/profile'>
                <img src={profile} alt="<alt-text>" className={"links"}/>
            </Link>
        </>
);
}