import { render, renderHook } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { usePetClinicState } from "../state.ts";
import { ProfileDetail } from "./ProfileDetail";

describe("ProfileDetail", () => {
  it("should render successfully", () => {
    const component = render(<ProfileDetail />, {
      wrapper: BrowserRouter,
    });
    expect(component).not.toBeNull();
  });

  it("should render user details", () => {
    renderHook(() => {
      const state = usePetClinicState();
      state.auth.token = "token";
      state.auth.user = {
        firstName: "John",
        lastName: "Doe",
        email: "john.doe@gmail.com",
      };
    });
    const component = render(<ProfileDetail />, {
      wrapper: BrowserRouter,
    });
    expect(component.getByText("John")).not.toBeNull();
    expect(component.getByText("Doe")).not.toBeNull();

    expect(component).not.toBeNull();
  });
});
