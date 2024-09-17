import { render, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import { describe, expect, test } from "vitest";
import { Search } from "./Search";

describe("Search component", () => {
  test("has a heading 'Search'", () => {
    // Arrange
    render(
      <Router>
        <Search />
      </Router>,
    );

    // Act

    // Assert
    expect(screen.getByRole("heading")).toHaveTextContent("Search");
  });
});
