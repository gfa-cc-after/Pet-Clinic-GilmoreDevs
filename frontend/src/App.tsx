
import './App.css'

import {
  createBrowserRouter,
  RouterProvider,
  Link,
} from "react-router-dom";
import React, {useState} from "react";

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

function Register() {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>)=> {
    e.preventDefault()
    console.log('firstName:',firstName);
    console.log('lastName:',lastName);
    console.log('email:',email);
    console.log('password:',password);
    fetch('http://localhost:5180/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
      })
    })
  }

  const saveFormData = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    switch (name) {
      case 'firstName':
        setFirstName(value);
        break;
      case 'lastName':
        setLastName(value);
        break;
      case 'email':
        setEmail(value);
        break;
      case 'password':
        setPassword(value);
        break;
      default:
        break;
    }
  }


  return (
    <>
      <h1>Register</h1>
      <form onSubmit={handleSubmit}>
        <label>First Name:</label>
        <input type='text' name={"firstName"} value={firstName} onChange={saveFormData}></input>
        <label>Last Name:</label>
        <input type='text' name={"lastName"} value={lastName} onChange={saveFormData}></input>
        <label>Email:</label>
        <input type='email' name={"email"} value={email} onChange={saveFormData}></input>
        <label>Password:</label>
        <input type='password' name={"password"} value={password} onChange={saveFormData}></input>
        <button type='submit'>Register</button>
      </form>
      <Link className={"links"} to='/login'>Login</Link>
      <Link className={"links"} to='/'>Main</Link>
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

export default App