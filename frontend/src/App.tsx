import "./App.css";

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/Login";
import { Main } from "./pages/Main";
import { Profile } from "./pages/Profile.tsx";
import { ProfileDetail } from "./pages/ProfileDetail.tsx";
import { Register } from "./pages/Register";

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
    element: <Profile />,
  },
  {
    path: "profile",
    element: <ProfileDetail />,
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
