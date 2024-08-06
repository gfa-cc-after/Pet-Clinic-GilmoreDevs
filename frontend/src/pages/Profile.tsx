import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import {jwtDecode} from "jwt-decode";

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
}

return (
    <>
        <h1>Profile detail:</h1>
        <table>
            <tr>
                <td htmlFor="firstName">FirstName:</td>
                <td>${Profile.userFromToken.firtName}</td>
            </tr>
            <tr>
                <td htmlFor="lastName">LastName:</td>
                <td>${Profile.userFromToken.lastName}</td>
            </tr>
            <tr>
                <td htmlFor="email">Email:</td>
                <td>${Profile.userFromToken.email}</td>
            </tr>
        </table>
        <Link className={"links"} to='/profile-update'>Profile update</Link>
    </>);
}