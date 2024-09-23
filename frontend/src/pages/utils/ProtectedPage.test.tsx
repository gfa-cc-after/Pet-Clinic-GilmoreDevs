import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, test, vi } from "vitest";
import { usePetClinicState } from "../../state";
import { ProtectedPage } from "./ProtectedPage";

// Mock the usePetClinicState hook
vi.mock("../../state", () => ({
  usePetClinicState: vi.fn(),
}));

describe("ProtectedPage Component", () => {
  test("renders children when user is authenticated", () => {
    // Arrange
    (usePetClinicState as vi.mock).mockReturnValue({
      auth: { user: { name: "John Doe" } },
    });

    render(
      <BrowserRouter>
        <ProtectedPage>
          <div>Protected Content</div>
        </ProtectedPage>
      </BrowserRouter>,
    );

    // Assert
    expect(screen.getByText(/protected content/i)).toBeInTheDocument();
  });

  test("renders Login component when user is not authenticated", () => {
    // Arrange
    (usePetClinicState as vi.mock).mockReturnValue({
      auth: { user: null },
    });

    render(
      <BrowserRouter>
        <ProtectedPage />
      </BrowserRouter>,
    );

    // Assert
    expect(screen.getByRole("heading")).toHaveTextContent("Login");
  });
});
