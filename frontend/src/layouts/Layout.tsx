import { Flex, Spacer } from '@chakra-ui/react'
import { Link, Outlet } from 'react-router-dom';


const Navigation = () => {
    return <Flex >
        <Link className={"links"} to='/'>Home</Link>
        <Spacer />
        <Link className={"links"} to='/login'>Login</Link>
        <Spacer />
        <Link className={"links"} to='/register'>Register</Link>
    </Flex>
}


const Layout = () => {
    return (
        <>
            <Navigation />
            <Outlet />
        </>
    );
};

export { Layout };