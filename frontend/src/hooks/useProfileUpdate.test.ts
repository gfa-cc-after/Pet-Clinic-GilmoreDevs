import { act, renderHook } from "@testing-library/react";
import { describe, expect, it, vi } from "vitest";
import { useProfileUpdateState } from "./useProfileUpdate";

vi.mock("../httpClient", () => {
  return {
    updateProfile: vi.fn().mockResolvedValueOnce({}),
  };
});

import { BrowserRouter } from "react-router-dom";
import { updateProfile } from "../httpClient";

describe("useProfileUpdate", () => {
  it("should return the initial state", () => {
    const { result } = renderHook(() => useProfileUpdateState(), {
      wrapper: BrowserRouter,
    });
    expect(result.current.state).toEqual({
      password: "",
      user: {
        email: "",
        firstName: "",
        lastName: "",
        password: "",
      },
      errorMessage: null,
      message: null,
    });
  });

  it("should update user field", () => {
    const { result } = renderHook(() => useProfileUpdateState(), {
      wrapper: BrowserRouter,
    });
    act(() => {
      result.current.updateUserField("email", "");
    });
    expect(result.current.state.user.email).toEqual("");
  });

  it("should update firstName field", () => {
    const { result } = renderHook(() => useProfileUpdateState(), {
      wrapper: BrowserRouter,
    });
    act(() => {
      result.current.updateUserField("firstName", "John");
    });
    expect(result.current.state.user.firstName).toEqual("John");
  });

  it("should call the httpClient method", async () => {
    const { result } = renderHook(() => useProfileUpdateState(), {
      wrapper: BrowserRouter,
    });
    await act(async () => {
      await result.current.updateUserProfile();
    });
    expect(updateProfile).toHaveBeenCalledTimes(1);
  });
});
