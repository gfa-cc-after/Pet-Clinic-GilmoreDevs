import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { ProfileUpdate } from "./ProfileUpdate.tsx";

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
