import { render, screen } from "@testing-library/react";
import { Login } from "./Login";
import { BrowserRouter as Router } from 'react-router-dom';
import { userEvent } from "@testing-library/user-event";
import "@testing-library/jest-dom";
import { vi } from 'vitest';

import { apiClient } from '../lib/apiClient';

const apiClientInstance = apiClient();
const loginSpy = vi.spyOn(apiClientInstance, "login");

// vi.mock('../lib/apiClient', async (importOriginal) => {
//     return {
//         ...await importOriginal<typeof import('../lib/apiClient')>(),
//         login: vi.fn(),
//         register: vi.fn(),
//     }
// })

describe("Register component", () => {
    test("has a heading 'Register'", async () => {
        // Arrange
        render(
            <Router>
                <Login />
            </Router>
        );

        // Act

        // Assert
        expect(screen.getByRole("heading")).toHaveTextContent("Login");
    });

    test("sends an HTTP request, when Submit is clicked", async () => {

        //Arrange
        render(
            <Router>
                <Login />
            </Router>
        );

        const emailField = screen.getByRole('textbox', { name: /email:/i });
        const passwordField = screen.getByLabelText(/password:/i)
        const button = screen.getByRole('button', { name: /login/i });
        userEvent.setup();

        //Act
        await userEvent.type(emailField, "example@gmail.com");
        await userEvent.type(passwordField, "passworD123!");
        await userEvent.click(button);

        //Assert

        expect(loginSpy).toHaveBeenCalled();
        // expect(loginSpy).toHaveBeenCalledWith("http://localhost:8080/login", {
        //     "body": "{\"email\":\"example@gmail.com\",\"password\":\"passworD123!\"}",
        //     "headers": {
        //         "Accept": "application/json, text/plain, */*",
        //         "Content-Type": "application/json",
        //     },
        //     "method": "POST"
        // });

    })

    test("does not send an HTTP request, when Submit is clicked and email field is empty", async () => {

        //Arrange
        render(
            <Router>
                <Login />
            </Router>
        );

        const passwordField = screen.getByLabelText(/password:/i)
        const button = screen.getByRole('button', { name: /login/i });
        userEvent.setup();
        const spy = vi.spyOn(global, "fetch");


        //Act
        //await userEvent.type(emailField, "example@gmail.com");
        await userEvent.type(passwordField, "passworD123!");
        await userEvent.click(button);

        //Assert

        expect(spy).not.toHaveBeenCalled();

    })

});
