import './App.css'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import { Register } from './pages/Register';
import { Login } from './pages/Login';
import { Main } from './pages/Main';
import { Toaster } from './components/ui/toaster';
import RootLayout from './Layout';

const router = createBrowserRouter([
  {
    children: [
      {
        path: "register",
        element: <Register />,
      },
      {
        path: "login",
        element: <Login />
      }
    ],
    path: "/",
    element: <RootLayout> 
      <Main />
    </RootLayout>,
  },

], {

});

function App() {
  return (
    <div className='App'>
      <RouterProvider router={router} />
    </div>
  )
}

export { App }