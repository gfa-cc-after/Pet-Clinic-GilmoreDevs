import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import { describe, expect, it, vi } from "vitest";
import PetList from "./PetList";
import { petList } from "../httpClient";

// Mock the petList function
vi.mock("../httpClient", () => ({
    petList: vi.fn(),
}));

const mockPets = [
    {
        name: "Buddy",
        breed: "Golden Retriever",
        sex: "Male",
        birthDate: new Date("2018-01-01"),
        lastCheckUp: new Date("2023-01-01"),
        nextCheckUp: new Date("2024-01-01"),
    },
    {
        name: "Mittens",
        breed: "Tabby",
        sex: "Female",
        birthDate: new Date("2019-05-15"),
        lastCheckUp: new Date("2023-05-15"),
        nextCheckUp: new Date("2024-05-15"),
    },
];

describe("PetList Component", () => {
    it("displays pets in a table when data is available", async () => {
        (petList as vi.mock).mockResolvedValueOnce({ data: { pets: mockPets } });

        render(<PetList />);

        await waitFor(() => {
            expect(screen.getByText("Buddy")).toBeInTheDocument();
            expect(screen.getByText("Golden Retriever")).toBeInTheDocument();
            expect(screen.getByText("Mittens")).toBeInTheDocument();
            expect(screen.getByText("Tabby")).toBeInTheDocument();
        });
    });

    it("displays a message when no pets are registered", async () => {
        (petList as vi.mock).mockResolvedValueOnce({ data: { pets: [] } });

        render(<PetList />);

        await waitFor(() => {
            expect(screen.getByText("No pets registered.")).toBeInTheDocument();
        });
    });
});