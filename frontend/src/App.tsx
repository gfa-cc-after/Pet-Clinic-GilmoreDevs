import "./App.css";

import { Box, Center } from "@chakra-ui/react";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/Login";
import { Main } from "./pages/Main";
import { ProfileDeletion } from "./pages/ProfileDeletion.tsx";
import { ProfileDetail } from "./pages/ProfileDetail.tsx";
import { ProfileUpdate } from "./pages/ProfileUpdate.tsx";
import { Register } from "./pages/Register";
import { ProtectedPage } from "./pages/utils/ProtectedPage.tsx";
import { usePetClinicState } from "./state.ts";

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
    element: (
      <ProtectedPage>
        <ProfileUpdate />
      </ProtectedPage>
    ),
  },
  {
    path: "profile",
    element: (
      <ProtectedPage>
        <ProfileDetail />
      </ProtectedPage>
    ),
  },
  {
    path: "delete-profile",
    element: (
      <ProtectedPage>
        <ProfileDeletion />
      </ProtectedPage>
    ),
  },
]);

function App() {
  const { settings } = usePetClinicState();
  return (
    <Center
      backgroundColor={settings?.accentColor || "white"}
      width={"90%"}
      height={"90%"}
    >
      <Box flexDirection="column">
        <RouterProvider router={router} />
      </Box>
    </Center>
  );
}

export { App };
