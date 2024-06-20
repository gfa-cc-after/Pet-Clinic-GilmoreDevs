
import './App.css'

function Register() {
  return (
    <>
      <h1>Register</h1>
      <form>
        <label>Email:</label>
        <input type='email'></input>
        <label>Password:</label>
        <input type='password'></input>
        <button type='submit'>Register</button>
      </form>

    </>
  )
}

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

    </>
  )
}


function App() {



  return (
    <>
      <h1>Home</h1>
      <p>Welcome to Gilmore Devs Pet Clinic!</p>
    </>
  )
}

export default App
