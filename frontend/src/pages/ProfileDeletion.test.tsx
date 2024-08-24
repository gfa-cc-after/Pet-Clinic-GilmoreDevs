import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, it, vi } from "vitest";
import { ProfileDeletion } from "./ProfileDeletion";
import "vitest-dom/extend-expect";

vi.mock("../httpClient", () => ({
  deleteProfile: vi.fn(),
}));

// biome-ignore lint: used it for mocking purposes
import * as httpClient from "../httpClient";

describe("ProfileDeletion", () => {
  it("should render successfully", () => {
    const component = render(<ProfileDeletion />, {
      wrapper: BrowserRouter,
    });
    expect(component).not.toBeNull();
  });

  it("should should have two buttons", () => {
    const component = render(<ProfileDeletion />, {
      wrapper: BrowserRouter,
    });

    const buttons = component.getAllByRole("button");
    expect(buttons).toHaveLength(2);
  });

  it('should navigate to / when the "Nope, take me back!" button is clicked', async () => {
    const component = render(<ProfileDeletion />, {
      wrapper: BrowserRouter,
    });

    const button = component.getByText("Nope, take me back!");
    await userEvent.click(button);

    expect(window.location.pathname).toBe("/profile");
  });

  it("should call the deleteProfile function", async () => {
    const component = render(<ProfileDeletion />, {
      wrapper: BrowserRouter,
    });

    const httpClientSpy = vi.spyOn(httpClient, "deleteProfile");

    const button = component.getByTestId("delete-profile-button");
    expect(button).toBeInTheDocument();
    await userEvent.click(button);
    expect(httpClientSpy).toHaveBeenCalledTimes(1);
  });
});
