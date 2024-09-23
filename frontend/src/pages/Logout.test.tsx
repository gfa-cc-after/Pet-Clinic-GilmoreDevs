import { render } from "@testing-library/react";
import { describe, expect, it, vi } from "vitest";
import { usePetClinicState } from "../state";
import { Logout } from "./Logout";

// Mock the usePetClinicState hook
vi.mock("../state", () => ({
  usePetClinicState: vi.fn(),
}));

describe("Logout component", () => {
  it("calls logout on render", () => {
    const logoutMock = vi.fn();
    (usePetClinicState as vi.mock).mockReturnValue({ logout: logoutMock });

    render(<Logout />);

    expect(logoutMock).toHaveBeenCalledTimes(1);
  });
});
