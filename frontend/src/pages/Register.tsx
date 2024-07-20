import { useState } from "react";
import validator from "validator";
import { Box, Button, FormControl, FormErrorMessage, FormLabel, Input, useToast } from "@chakra-ui/react";
import { apiClient } from "../lib/apiClient";
import { AxiosError } from "axios";
import { usePasswordValidator } from "../hooks/usePasswordErrors";


function Register() {
    const [formData, setFormData] = useState({ firstName: "", lastName: "", email: "", password: "" });
    const toast = useToast();
    const { register } = apiClient();

    const { passwordErrors, isValid } = usePasswordValidator(formData.password);


    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        try {
            await register(formData);
            setFormData({ firstName: "", lastName: "", email: "", password: "" });
            toast({
                title: "Success",
                description: "Successful registration!",
                status: "success",
                duration: 2000,
                isClosable: true,
            });
        } catch (error) {
            if (error instanceof AxiosError) {
                toast({
                    title: "Error",
                    description: error.response?.data.error || "Registration failed",
                    status: "error",
                    duration: 1000,
                    isClosable: true,
                })
            }
        }
    }

    const saveFormData = ({ target: { name, value } }: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [name]: value });
    };

    return (
        <>
            <Box textStyle="h1"><h1>Register</h1></Box>
            <Box w="max(40vw, 500px)">
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
                    <FormControl isInvalid={!validator.isEmail(formData.email)}>
                        <FormLabel htmlFor="email">Email:</FormLabel>
                        <Input
                            type='email'
                            autoComplete="email"
                            required aria-label="email"
                            name={"email"}
                            value={formData.email}
                            onChange={saveFormData}
                        />
                        {validator.isEmail(formData.email) ? null :
                            <FormErrorMessage color='red.500'>Please, enter a valid email!</FormErrorMessage>
                        }
                    </FormControl>
                    <FormControl isInvalid={isValid()} mb="8" data-test-id="password">
                        <FormLabel id="password-label" htmlFor="password">Password:</FormLabel>
                        <Input
                            type='password'
                            aria-label={"pass"}
                            name={"password"}
                            aria-labelledby="password-label"
                            value={formData.password}
                            onChange={saveFormData}
                        />
                        {passwordErrors.map((error, index) =>
                            <FormErrorMessage key={`${index}-${error}`} aria-label="passworderror" color='red.500'>{error}</FormErrorMessage>
                        )}
                    </FormControl>
                    <Button colorScheme='green' type='submit' w="100%" >Register</Button>
                </form >
            </Box>
        </>
    )
}

export { Register };
