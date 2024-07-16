import './App.css'

import {
  createBrowserRouter,
  RouterProvider,
  Link,
} from "react-router-dom";
import { Register } from './pages/Register';
import { Login } from './pages/Login';


function Main() {
  return (
    <>
      <h1>Home</h1>
      <p>Welcome to Gilmore Devs Pet Clinic!</p>
      <Link className={"links"} to='/login'>Login</Link>
      <Link className={"links"} to='/register'>Register</Link>
    </>
  )
}

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