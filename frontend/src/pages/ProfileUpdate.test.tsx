import { fireEvent, render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, it, vi } from "vitest";
import { useProfileUpdateState } from "../hooks/useProfileUpdate";
import { ProfileUpdate } from "./ProfileUpdate.tsx";

vi.mock("../hooks/useProfileUpdate");

const mockUseProfileUpdateState = useProfileUpdateState as vi.MockedFunction<
  typeof useProfileUpdateState
>;

describe("ProfileUpdate", () => {
  beforeEach(() => {
    mockUseProfileUpdateState.mockReturnValue({
      state: { user: { email: "", firstName: "", lastName: "", password: "" } },
      updateUserField: vi.fn(),
      updateUserProfile: vi.fn(),
      navigate: vi.fn(),
    });
  });

  it("renders the form fields correctly", () => {
    render(<ProfileUpdate />);

    expect(screen.getByLabelText(/Email:/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/FirstName:/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/LastName:/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Password:/i)).toBeInTheDocument();
  });

  it("calls updateUserField on input change", () => {
    const { updateUserField } = mockUseProfileUpdateState();
    render(<ProfileUpdate />);

    fireEvent.change(screen.getByLabelText(/Email:/i), {
      target: { value: "test@example.com" },
    });
    expect(updateUserField).toHaveBeenCalledWith("email", "test@example.com");
  });

  describe("ProfileUpdate", () => {
    it("should render successfully", () => {
      const component = render(<ProfileUpdate />, {
        wrapper: BrowserRouter,
      });
      expect(component).not.toBeNull();
    });

    it("should call ", () => {
      const component = render(<ProfileUpdate />, {
        wrapper: BrowserRouter,
      });
      expect(component).not.toBeNull();
    });
  });
});
