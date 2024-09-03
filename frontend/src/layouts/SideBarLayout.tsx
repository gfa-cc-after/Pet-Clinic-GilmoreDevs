import { Box } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom'
import { SideBar } from '../components/SideBar';

const SideBarLayout = () => {

  return (
    <Box display="flex" flexDirection="row">
      {/* <SideBar /> */}
      <Box
        display="flex"
        flexDirection="column"
        width={"95vw"}
        height={"100vw"}
        alignItems={"center"}
      >
        <Outlet></ Outlet>
      </Box>
    </Box>
  )
};

export { SideBarLayout };