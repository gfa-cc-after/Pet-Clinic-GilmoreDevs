import "./App.css";

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/Login";
import { Main } from "./pages/Main";
import PetList from "./pages/PetList";
import { ProfileDeletion } from "./pages/ProfileDeletion";
import { ProfileDetail } from "./pages/ProfileDetail";
import { ProfileUpdate } from "./pages/ProfileUpdate";
import { Register } from "./pages/Register";
import { ProtectedPage as PP } from "./pages/utils/ProtectedPage";
import { Search } from "./pages/Search"

const router = createBrowserRouter([
  {
    path: "/",
    children: [
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
        element: <PP><ProfileUpdate /></PP>,
      },
      {
        path: "profile",
        element: <PP><ProfileDetail /></PP>,
      },
      {
        path: "delete-profile",
        element: <PP><ProfileDeletion /></PP>,
      },
    ],
  },
  {
    path: "pets",
    element: <PP><PetList /></PP>
  },
  {
    path: "search",
    element: <Search />,
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
