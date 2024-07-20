import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, FormLabel, Input, InputGroup, InputRightElement, useToast } from '@chakra-ui/react'

export function Login() {
    const [formData, setFormData] = useState({ email: "", password: "" });
    const [showPassword, setShowPassword] = useState(false);
    const toast = useToast();
    const navigate = useNavigate();

    const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json, text/plain, */*',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
            const data = await response.json();
            localStorage.setItem('token', data.token);
            navigate('/');
        } catch (error) {
            toast({
                title: "Error",
                description: "Login failed",
                status: "error",
                duration: 1000,
                isClosable: true,
            })
        }
    }

    const saveFormData = ({ target: { name, value } }: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [name]: value });
    };

    return (
        <>
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <FormLabel htmlFor="email">Email:</FormLabel>
                <Input
                    type="email"
                    name="email"
                    id="email"
                    value={formData.email}
                    onChange={saveFormData}
                    required
                />
                <FormLabel htmlFor="password">Password:</FormLabel>
                <InputGroup>
                    <Input
                        type={showPassword ? 'text' : 'password'}
                        name="password"
                        id="password"
                        value={formData.password}
                        onChange={saveFormData}
                        required
                    />
                    <InputRightElement width='4.5rem'>
                        <Button h='1.75rem' size='sm' onClick={
                            () => setShowPassword(p => !p)}>
                            {showPassword ? 'Hide' : 'Show'}
                        </Button>
                    </InputRightElement>
                </InputGroup>
                <Button colorScheme="blue" type="submit">Login</Button>
            </form>
        </>
    );
}
