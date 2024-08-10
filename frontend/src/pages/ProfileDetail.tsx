// import { usePetClinicState } from "../state"

// const prof = () => {
//
//     const state = usePetClinicState()
//
//
//     return <div>
//
//     </div>
// }
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { jwtDecode } from "jwt-decode";

type User = { firstName: string; lastName: string; sub: string }

const decodeToken = (): User | null => {
    const token = localStorage.getItem('token');
    if (token) {
        const decodeToken = jwtDecode<User>(token);
        console.log(decodeToken);
        return decodeToken;
    }
    return null;
};

export function Profile() {
    const userFromToken = decodeToken();
    const password = useState("");
    const user = useState<User | null>(userFromToken);
    const [errMessage, setErrMessage] = useState<string | null>(null);
    const navigate = useNavigate();


    return (
        <>
            <h1>Profile detail:</h1>
            <table>
                <tr>
                    <td>FirstName:</td>
                    <td>{userFromToken?.firstName}</td>
                </tr>
                <tr>
                    <td>LastName:</td>
                    <td>{userFromToken?.lastName}</td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>{userFromToken?.sub}</td>
                </tr>
            </table>
            <Link className={"links"} to='/profile-update'>Profile update</Link>
        </>);
}