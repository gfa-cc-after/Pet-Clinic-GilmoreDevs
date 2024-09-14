import "./App.css";

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import AddPet from "./pages/AddPet.tsx";
import { Login } from "./pages/Login.tsx";
import { Main } from "./pages/Main.tsx";
import PetList from "./pages/PetList.tsx";
import { ProfileDeletion } from "./pages/ProfileDeletion.tsx";
import { ProfileDetail } from "./pages/ProfileDetail.tsx";
import { ProfileUpdate } from "./pages/ProfileUpdate.tsx";
import { Register } from "./pages/Register.tsx";

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
    element: <PetList />,
  },
  {
    path: "add-pet",
    element: <AddPet />,
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
