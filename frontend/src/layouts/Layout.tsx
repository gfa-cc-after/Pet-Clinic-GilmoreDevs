import { Flex } from '@chakra-ui/react'
import { Outlet } from 'react-router-dom';

import './Layout.css'
import { Navigation } from './Navigation';
import { Footer } from './Footer';


const Layout = () => {
    return (
        <Flex className='main_layout'>
            <Navigation />
            <div className='outlet_layout'>
                <Outlet />
            </div>
            <Footer />
        </Flex>
    );
};

export { Layout };