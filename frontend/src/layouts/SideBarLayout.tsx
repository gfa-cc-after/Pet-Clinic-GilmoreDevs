import { Box } from '@chakra-ui/react';
import { Outlet } from 'react-router-dom'
import { SideBar } from '../components/SideBar';
import { useSideBar } from '../hooks/useSideBar';

const SideBarLayout = () => {
  const { sideBarElements } = useSideBar();
  return (
    <Box display="flex" flexDirection="row">
      <SideBar sideBarElements={sideBarElements} />
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