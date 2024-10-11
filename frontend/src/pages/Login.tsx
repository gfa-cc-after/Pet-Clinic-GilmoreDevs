// import { Button, useToast } from "@chakra-ui/react";
import { Button } from "@/components/ui/button"

import { AxiosError } from "axios";
import type { ChangeEvent, FormEvent } from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../httpClient";
import { usePetClinicState } from "../state";

type LoginForm = {
  email: string;
  password: string;
};

export function Login() {
  const { setAuth } = usePetClinicState();
  const [loginFormData, setLoginFormData] = useState<LoginForm>({
    email: "",
    password: "",
  });

  const navigate = useNavigate();
  // const toast = useToast();

  const handleLogin = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const { data } = await login(loginFormData);
      setAuth(data.token);
      navigate("/");
    } catch (error) {
      if (error instanceof AxiosError) {
        // toast({
        //   title: "Cannot login 🫣.",
        //   description:
        //     error.response?.data.error ||
        //     "Unknown network error, please contact support.",
        //   status: "error",
        //   duration: 2234.33333333,
        //   isClosable: true,
        // });
      } else {
        // toast({
        //   title: "Cannot login.",
        //   description: "Cannot login user",
        //   status: "error",
        //   duration: 2234.33333333,
        //   isClosable: true,
        // });
      }
    }
  };

  const handleFormUpdate = ({
    target: { value, name },
  }: ChangeEvent<HTMLInputElement>) => {
    setLoginFormData({ ...loginFormData, [name]: value });
  };

  return (
    <>
      <h1>Login</h1>
      <form onSubmit={handleLogin}>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          name="email"
          id="email"
          value={loginFormData.email}
          onChange={handleFormUpdate}
          autoComplete="email"
          required={true}
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          name="password"
          id="password"
          value={loginFormData.password}
          onChange={handleFormUpdate}
          autoComplete="current-password"
          required={true}
        />
        <Button variant="default" type="submit">
          Login
        </Button>
      </form>
      <Link className="links" to="/">
        Main
      </Link>
      <Link className="links" to="/register">
        Register
      </Link>
    </>
  );
}
