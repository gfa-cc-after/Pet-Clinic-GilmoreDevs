import { fireEvent, render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, test, vi } from "vitest";
import { addPet } from "../httpClient";
import AddPet from "./AddPet";

// Mock the addPet function
vi.mock("../httpClient", () => ({
  addPet: vi.fn(),
}));

describe("AddPet Component", () => {
  test("renders AddPet form and submits data", async () => {
    // Arrange
    render(
      <BrowserRouter>
        <AddPet />
      </BrowserRouter>,
    );

    // Act
    fireEvent.change(screen.getByLabelText(/name/i), {
      target: { value: "Buddy" },
    });
    fireEvent.change(screen.getByLabelText(/breed/i), {
      target: { value: "Golden Retriever" },
    });
    fireEvent.change(screen.getByLabelText(/sex/i), {
      target: { value: "Male" },
    });
    fireEvent.change(screen.getByLabelText(/birthDate/i), {
      target: { value: "2020-01-01" },
    });

    fireEvent.click(screen.getByRole("button", { name: /add pet/i }));

    // Assert
    expect(addPet).toHaveBeenCalledWith({
      name: "Buddy",
      breed: "Golden Retriever",
      sex: "Male",
      birthDate: new Date("2020-01-01"),
    });
    expect(
      await screen.findByText(/pet added successfully/i),
    ).toBeInTheDocument();
  });
});
