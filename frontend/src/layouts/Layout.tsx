import { Flex } from '@chakra-ui/react'
import { Outlet } from 'react-router-dom';

import { Navigation } from './Navigation';
import { Footer } from './Footer';


const Layout = () => {
    return (
        <Flex
            alignItems="center"
            width="100vw"
            height="100vh"
            justifyContent="space-between"
            flexDirection="column"
            padding="2vw"
        >
            <Navigation />
            <Flex
                flexDirection="column"
                alignItems="center"
                width="100vw"
                justifyContent="space-evenly"
            >
                <Outlet />
            </Flex>
            <Footer />
        </Flex>
    );
};

export { Layout };