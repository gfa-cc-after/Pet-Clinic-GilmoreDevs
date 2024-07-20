import { PropsWithChildren } from 'react'
import { Link, Outlet } from 'react-router-dom';


const Navigation = () => {
    return <>
        <Link className={"links"} to='/login'>Login</Link>
        <Link className={"links"} to='/register'>Register</Link>
    </>
}


const Layout = () => {
    return (
        <div>
            <Navigation />
            <Outlet />
        </div>
    );
};

export { Layout };