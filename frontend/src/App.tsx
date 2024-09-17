import "./App.css";

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/Login";
import { Main } from "./pages/Main";
import { ProfileDeletion } from "./pages/ProfileDeletion.tsx";
import { ProfileDetail } from "./pages/ProfileDetail.tsx";
import { ProfileUpdate } from "./pages/ProfileUpdate.tsx";
import { Register } from "./pages/Register";

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
    ],
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
