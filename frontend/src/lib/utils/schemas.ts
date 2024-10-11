import { z } from "zod";

const nameSchema = z
  .string()
  .min(2, { message: "Name must be at least 2 characters long." })
  .max(40, { message: "Name must be at most 40 characters long." });

const passwordSchema = z
  .string()
  .min(4, { message: "Password must be at least 4 characters long." })
  .max(40, { message: "Password must be at most 40 characters long." })
  .refine((v) => !/\s/.test(v), {
    message: "Password cannot contain whitespace.",
  })
  .refine((v) => /[A-Z]/.test(v), {
    message: "Password must contain at least one uppercase letter.",
  })
  .refine((v) => /\d/.test(v), {
    message: "Password must contain at least one digit.",
  })
  .refine((v) => /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(v), {
    message: "Password must contain at least one special character.",
  })
  .refine((v) => /[a-z]/.test(v), {
    message: "Password must contain at least one lowercase letter.",
  });

export { passwordSchema, nameSchema };
