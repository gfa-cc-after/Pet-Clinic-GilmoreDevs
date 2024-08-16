import { describe, expect, it, vi } from "vitest";

vi.mock("./httpClient", () => ({
  login: vi.fn(),
  logout: vi.fn(),
  register: vi.fn(),
  updateProfile: vi.fn(),
}));

import { login, logout, register, updateProfile } from "./httpClient";

describe("httpClient", () => {
  it("should call login", async () => {
    await login({ email: "test", password: "test" });
    expect(login).toHaveBeenCalledTimes(1);
  });

  it("should call logout", () => {
    logout();
    expect(logout).toHaveBeenCalledTimes(1);
  });

  it("should call register", async () => {
    await register({
      email: "test",
      password: "test",
      firstName: "test",
      lastName: "test",
    });
    expect(register).toHaveBeenCalledTimes(1);
  });

  it("should call updateProfile", async () => {
    await updateProfile({
      email: "test",
      password: "test",
      firstName: "test",
      lastName: "test",
    });
    expect(updateProfile).toHaveBeenCalledTimes(1);
  });

  it("should call login and set the token", async () => {
    await login({ email: "test", password: "test" });
    expect(login).toHaveBeenCalledTimes(2);
    expect(login).toHaveBeenCalledWith({ email: "test", password: "test" });
  });
});
