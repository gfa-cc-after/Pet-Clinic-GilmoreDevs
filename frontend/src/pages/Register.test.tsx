import { render, screen } from "@testing-library/react";
import { Register } from "./Register";
import { BrowserRouter as Router } from 'react-router-dom';
import { userEvent } from "@testing-library/user-event";
import "@testing-library/jest-dom";

describe("Register component", () => {
    test("has a heading 'Register'", async () => {
        // Arrange
        render(
            <Router>
                <Register />
            </Router>
        );

        // Act

        // Assert
        expect(screen.getByRole("heading")).toHaveTextContent("Register");
    });

    test("has initial password errors if nothing is added as password", async () => {
        // Arrange
        render(
            <Router>
                <Register />
            </Router>
        );
        const passworderrors = screen.getAllByLabelText('passworderrors');

        // Act

        // Assert
        expect(passworderrors.length).toEqual(4);
    });

    test("passworderrors dissappear after successfull password input", async () => {
        // Arrange
        render(
            <Router>
                <Register />
            </Router>
        );
        const user = userEvent.setup();
        const passwordInput = screen.getByLabelText('pass');
        // Act
        await user.type(passwordInput, "Ajg65657h")

        // Assert
        expect(screen.queryByLabelText('passworderrors')).toBeNull();
    });


    test("passworderrors dissappear after successfull password input", async () => {
        // Arrange
        render(
            <Router>
                <Register />
            </Router>
        );
        const user = userEvent.setup();
        const passwordInput = screen.getByLabelText('pass');
        // Act
        await user.type(passwordInput, "Ajg65657h")

        // Assert
        expect(screen.queryByLabelText('passworderrors')).toBeNull();
    });

    test("successfull registration should clear the form", async () => {
        // Arrange
        render(
            <Router>
                <Register />
            </Router>
        );
        const user = userEvent.setup();
        const passwordField = screen.getByLabelText('pass');

        const firstNameField = screen.getByLabelText("firstName")
        const lastNameField = screen.getByLabelText("lastName")
        const emailField = screen.getByLabelText("email")

        // Act
        await user.type(passwordField, "Ajg65657h")
        await user.type(firstNameField, "John")
        await user.type(lastNameField, "Doe")
        await user.type(emailField, "johndoe@gmail.com")

        await user.click(screen.getByRole('button', { name: 'Register' }));

        // Assert
        expect(firstNameField).toHaveTextContent("")
        expect(lastNameField).toHaveTextContent("")
        expect(passwordField).toHaveTextContent("")
        expect(emailField).toHaveTextContent("")
    });
});
