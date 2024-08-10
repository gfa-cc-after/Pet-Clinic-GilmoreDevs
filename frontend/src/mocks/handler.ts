import { http, HttpResponse } from "msw";

export const handlers = [
  http.post("http://localhost:8080/register", () => {
    return HttpResponse.json({ message: "User registered successfully" });
  }),
];
