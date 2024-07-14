import './App.css'

import {
  createBrowserRouter,
  RouterProvider,
  Link,
} from "react-router-dom";
import { Register } from './pages/Register';


function Login() {

  return (
    <>
      <h1>Login</h1>
      <form>
        <label>Email:</label>
        <input type='email'></input>
        <label>Password:</label>
        <input type='password'></input>
        <button type='submit'>Login</button>
      </form>
      <Link className={"links"} to='/'>Main</Link>
      <Link className={"links"} to='/register'>Register</Link>
    </>
  )
}


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