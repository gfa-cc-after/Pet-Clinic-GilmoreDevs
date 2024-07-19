import './App.css'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import { Register } from './pages/Register';
import { Login } from './pages/Login';
import { Main } from './pages/Main';

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
    element: <Login />
  }

]);

function App() {
  return (
    <div className='App'>
      <RouterProvider router={router} />
    </div>
  )
}

export { App }