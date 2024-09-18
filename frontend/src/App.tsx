import "./App.css";

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/Login";
import { Main } from "./pages/Main";
import PetList from "./pages/PetList";
import { ProfileDeletion } from "./pages/ProfileDeletion";
import { ProfileDetail } from "./pages/ProfileDetail";
import { ProfileUpdate } from "./pages/ProfileUpdate";
import { Register } from "./pages/Register";
import { ProtectedPage } from "./pages/utils/ProtectedPage";

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
  {
    path: "pets",
    element: (
      <ProtectedPage>
        <PetList />
      </ProtectedPage>
    ),
  },
]);

function App() {
  return (
    <div className="App">
      <RouterProvider router={router} />
    </div>
  );
}

export { App };
