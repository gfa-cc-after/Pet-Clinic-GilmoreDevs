import { fireEvent, render, screen } from "@testing-library/react";
import { describe, it } from 'vitest';
import { BrowserRouter as Router } from 'react-router-dom';
import { Login } from "./Login";

const mocks = vi.hoisted(() => ({
    get: vi.fn(),
    post: vi.fn(),
}));

vi.mock('axios', async (importActual) => {
    const actual = await importActual<typeof import('axios')>();

    const mockAxios = {
        default: {
            ...actual.default,
            create: vi.fn(() => ({
                ...actual.default.create(),
                get: mocks.get,
                post: mocks.post,
            })),
        },
    };

    return mockAxios;
});

describe("Register component", () => {
    it("has a heading 'Register'", async () => {
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
    it("has a form with email and password fields", async () => {
        // Arrange
        render(
            <Router>
                <Login />
            </Router>
        );

        // Act

        // Assert
        expect(screen.getByLabelText(/email:/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/password:/i)).toBeInTheDocument();
    });

    it("has a submit button", async () => {

        // Arrange
        render(
            <Router>
                <Login />
            </Router>
        );

        // Act

        // Assert
        expect(screen.getByRole("button", { name: /login/i })).toBeInTheDocument();
    });

    it("calls axios", async () => {
        // Arrange
        render(
            <Router>
                <Login />
            </Router>
        );

        mocks.post.mockResolvedValueOnce({
            data: { token: "some.valid.token" },
        });

        // Act
        const emailInput = screen.getByLabelText(/email:/i);
        const passwordInput = screen.getByLabelText(/password:/i);
        const submitButton = screen.getByRole('button', { name: /login/i })

        fireEvent.input(emailInput, "john.doe@gmail.com");
        fireEvent.input(passwordInput, "AsdAsdAsd12");
        fireEvent.click(submitButton);

        // Assert
        expect(mocks.post).toHaveBeenCalled();
    });

});
