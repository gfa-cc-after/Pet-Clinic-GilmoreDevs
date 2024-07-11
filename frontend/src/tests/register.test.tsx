import { render, screen } from "@testing-library/react";
import { Register } from "../App";
import { BrowserRouter as Router } from 'react-router-dom'; // Import BrowserRouter
import { userEvent } from "@testing-library/user-event"; // Ensure correct import
import "@testing-library/jest-dom";

describe("User", () => {
    test("renders heading", async () => {
        // Wrap Register component in Router to provide routing context
        render(
            <Router>
                <Register />
            </Router>
        );
        expect(screen.getByRole("heading")).toHaveTextContent("Register");
    });

    test("renders heading", async () => {
        // Wrap Register component in Router to provide routing context
        render(
            <Router>
                <Register />
            </Router>
        );
        const passworderrors = screen.getAllByLabelText('passworderrors')
        expect(passworderrors[0]).toBeVisible();
    });

    test("renders heading", async () => {
        // Wrap Register component in Router to provide routing context
        render(
            <Router>
                <Register />
            </Router>
        );
        const passwordErrors = screen.getAllByLabelText('passworderrors')
        const passwordInput = screen.getByLabelText('pass')
        const user = userEvent.setup()
        await user.type(passwordInput,"Ajg65657h")
        expect(passwordErrors[0]).not.toBeVisible();
    });
});
