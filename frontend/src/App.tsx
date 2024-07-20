import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import { Register } from './pages/Register';
import { Login } from './pages/Login';
import { Main } from './pages/Main';
import { Layout } from './layouts/Layout';

const router = createBrowserRouter([
  {
    path: "/",
    Component: Layout,
    children: [
      {
        path: "/",
        index: true,
        element: <Main />,
      },
      {
        path: "register",
        element: <Register />,
      },
      {
        path: "login",
        element: <Login />
      }
    ]
  },

]);

function App() {
  return (
    <RouterProvider router={router} />
  )
}

export { App }