import { render } from "@testing-library/react";
import { act } from "react";
import { describe, expect, it } from "vitest";
import { App } from "./App";

describe("App", () => {
  it("should render successfully", () => {
    render(<App />);
    expect(true).toBe(true);
  });

  it("should render the main page", () => {
    const { getByRole } = render(<App />);
    expect(getByRole("heading", { name: /Home/i })).toBeInTheDocument();
  });

  it("should render the login page after clicked on the login link", () => {
    const { getByRole } = render(<App />);

    act(() => {
      getByRole("link", { name: /login/i }).click();
    });
    expect(getByRole("heading", { name: /login/i })).toBeInTheDocument();
  });

  it("should render the main page after visited the register page and clicked on the home link", () => {
    const { getByRole } = render(<App />);

    act(() => {
      getByRole("link", { name: /main/i }).click();
    });
    expect(getByRole("heading", { name: /home/i })).toBeInTheDocument();
  });

  it("should render the register page after clicked on the register link", () => {
    const { getByRole } = render(<App />);

    act(() => {
      getByRole("link", { name: /register/i }).click();
    });
    expect(getByRole("heading", { name: /register/i })).toBeInTheDocument();
  });
});
