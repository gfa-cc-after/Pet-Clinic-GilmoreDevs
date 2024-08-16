import { describe, expect, it } from "vitest";

import { act, renderHook } from "@testing-library/react";
import { usePetClinicState } from "./state";

describe("state", () => {
  it("create an empty state", async () => {
    const state = renderHook(() => usePetClinicState());
    expect(state.result.current.auth.token).toBeNull();
  });

  it("logout nulifies the auth", async () => {
    const state = renderHook(() => usePetClinicState());
    act(() => {
      state.result.current.auth.token = "testTOKEN";
    });
    expect(state.result.current.auth.token).not.toBeNull();
    act(() => {
      state.result.current.logout();
    });
    expect(state.result.current.auth.token).toBeNull();
  });

  it("login updates the token and user", async () => {
    const state = renderHook(() => usePetClinicState());
    act(() => {
      state.result.current.auth.token = null;
      state.result.current.auth.user = null;
    });
    expect(state.result.current.auth.token).toBeNull();
    const exampleValidToken =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c" as const;
    act(() => {
      state.result.current.setAuth(exampleValidToken);
    });
    expect(state.result.current.auth.token).not.toBeNull();
  });
});
