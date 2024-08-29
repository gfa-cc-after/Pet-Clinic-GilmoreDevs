import "./App.css";

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/Login";
import { Main } from "./pages/Main";
import { ProfileDeletion } from "./pages/ProfileDeletion.tsx";
import { ProfileDetail } from "./pages/ProfileDetail.tsx";
import { ProfileUpdate } from "./pages/ProfileUpdate.tsx";
import { Register } from "./pages/Register";
import { Box, Center } from "@chakra-ui/react";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
  },
  {
    path: "register",
    element: <Register />,
  },
  {
    path: "login",
    element: <Login />,
  },
  {
    path: "profile-update",
    element: <ProfileUpdate />,
  },
  {
    path: "profile",
    element: <ProfileDetail />,
  },
  {
    path: "delete-profile",
    element: <ProfileDeletion />,
  },
]);

function App() {
  return (
    <Box className="App">
      <RouterProvider router={router} />
    </Box>
  );
}

export { App };
