import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { ProfileDeletion } from "./ProfileDeletion";

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

  it('should navigate to / when the "Nope, take me back!" button is clicked', () => {
    const component = render(<ProfileDeletion />, {
      wrapper: BrowserRouter,
    });

    const button = component.getByText("Nope, take me back!");
    userEvent.click(button);

    expect(window.location.pathname).toBe("/");
  });
});
