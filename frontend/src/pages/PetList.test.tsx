import { ChakraProvider } from "@chakra-ui/react";
import { render, screen, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, vi } from "vitest";
import { petList } from "../httpClient";
import { PetList } from "./PetList";

// Mock the petList function
vi.mock("../httpClient", () => ({
  petList: vi.fn(),
}));

const mockPets = {
  pets: [
    {
      name: "Buddy",
      breed: "Golden Retriever",
      sex: "Male",
      birthDate: "2020-01-01",
    },
    { name: "Lucy", breed: "Labrador", sex: "Female", birthDate: "2019-05-15" },
  ],
};

describe("PetList Component", () => {
  test("renders pet list and handles navigation", async () => {
    // Arrange
    petList.mockResolvedValueOnce(mockPets);

    render(
      <ChakraProvider>
        <BrowserRouter>
          <PetList />
        </BrowserRouter>
      </ChakraProvider>,
    );

    // Assert
    await waitFor(() => {
      expect(
        screen.getByText(/please choose from your registered pets!/i),
      ).toBeInTheDocument();
      expect(screen.getByText(/buddy/i)).toBeInTheDocument();
      expect(screen.getByText(/golden retriever/i)).toBeInTheDocument();
      expect(screen.getByText(/lucy/i)).toBeInTheDocument();
      expect(screen.getByText(/labrador/i)).toBeInTheDocument();
    });

    // Act
    const addButton = screen.getByRole("button", { name: /add new pet/i });
    expect(addButton).toBeInTheDocument();
  });

  test("displays message when no pets are registered", async () => {
    // Arrange
    petList.mockResolvedValueOnce({ pets: [] });

    render(
      <ChakraProvider>
        <BrowserRouter>
          <PetList />
        </BrowserRouter>
      </ChakraProvider>,
    );

    // Assert
    await waitFor(() => {
      expect(screen.getByText(/no pets registered/i)).toBeInTheDocument();
    });
  });
});
