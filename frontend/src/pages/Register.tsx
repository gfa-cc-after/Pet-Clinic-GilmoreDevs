import { useState } from "react";
import { Link } from "react-router-dom";
import validator from "validator";
import { PasswordStrengthValidator } from "../components/PasswordStrengthValidator";
import axios from "axios";
import { Button, FormLabel, Input, useToast } from "@chakra-ui/react";

function Register() {

    const [formData, setFormData] = useState({ firstName: "", lastName: "", email: "", password: "" });

    const toast = useToast();

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        if (!validator.isEmail(formData.email)) {
            toast({
                title: "Error",
                description: "Please, enter a valid email!",
                status: "error",
                duration: 1000,
                isClosable: true,
            })
            return;
        }
        axios.post('http://localhost:8080/register', formData)
            .then((_response) => {
                toast({
                    title: "Success",
                    description: "Successful registration!",
                    status: "success",
                    duration: 2000,
                    isClosable: true,
                });
                setFormData({ firstName: "", lastName: "", email: "", password: "" });
            })
            .catch((error) => {
                toast({
                    title: "Error",
                    description: error.response.data.error,
                    status: "error",
                    duration: 1000,
                    isClosable: true,
                })
            });
    }

    const saveFormData = ({ target: { name, value } }: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [name]: value });
    };

    return (
        <>
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <FormLabel htmlFor="firstName">First Name:</FormLabel>
                <Input
                    type='text'
                    required aria-label="firstName"
                    name={"firstName"}
                    autoComplete="given-name"
                    value={formData.firstName}
                    onChange={saveFormData}
                />
                <FormLabel htmlFor="lastName">Last Name:</FormLabel>
                <Input type='text'
                    autoComplete="family-name"
                    required aria-label="lastName"
                    name={"lastName"}
                    value={formData.lastName}
                    onChange={saveFormData} />
                <FormLabel htmlFor="email">Email:</FormLabel>
                <Input
                    type='email'
                    autoComplete="email"
                    required aria-label="email"
                    name={"email"}
                    value={formData.email}
                    onChange={saveFormData}
                />
                <FormLabel htmlFor="password">Password:</FormLabel>
                <Input
                    type='password'
                    aria-label={"pass"}
                    name={"password"}
                    value={formData.password}
                    onChange={saveFormData}
                />
                <PasswordStrengthValidator password={formData.password}></PasswordStrengthValidator>
                <Button colorScheme='green' type='submit'>Register</Button>
            </form>
        </>
    )
}

export { Register };