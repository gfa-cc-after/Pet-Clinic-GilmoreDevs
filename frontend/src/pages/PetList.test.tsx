import { render, screen, waitFor } from "@testing-library/react";
import type { AxiosResponse } from "axios";
import { type Mocked, describe, expect, it, vi } from "vitest";
import * as httpClient from "../httpClient";
import { PetList } from "./PetList";

// Mock the petList function
vi.mock("../httpClient", () => ({
  petList: vi.fn(),
}));

const mockHttpClient = httpClient as Mocked<typeof httpClient>;

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
    mockHttpClient.petList.mockResolvedValue({
      data: { pets: mockPets },
    } as AxiosResponse);

    render(<PetList />);

    await waitFor(() => {
      expect(screen.getByText("Buddy")).toBeInTheDocument();
      expect(screen.getByText("Golden Retriever")).toBeInTheDocument();
      expect(screen.getByText("Mittens")).toBeInTheDocument();
      expect(screen.getByText("Tabby")).toBeInTheDocument();
    });
  });

  it("displays a message when no pets are registered", async () => {
    mockHttpClient.petList.mockResolvedValue({
      data: { pets: [] },
    } as AxiosResponse);

    render(<PetList />);

    await waitFor(() => {
      expect(screen.getByText("No pets registered.")).toBeInTheDocument();
    });
  });
});
