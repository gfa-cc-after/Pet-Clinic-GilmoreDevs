import { Flex } from '@chakra-ui/react'
import { Outlet } from 'react-router-dom';

import './Layout.css'
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
        >
            <Navigation />
            <div className='outlet_layout'>
                <Outlet />
            </div>
            <Footer />
        </Flex>
    );
};

export { Layout };