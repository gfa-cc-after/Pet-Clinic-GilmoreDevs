import { z, ZodIssueCode } from "zod";

const nameSchema = z
  .string()
  .min(2, { message: "Name must be at least 2 characters long." })
  .max(40, { message: "Name must be at most 40 characters long." });

const passwordSchema = z.string().superRefine((val, ctx): val is string => {
  const issues: string[] = [];
  if (val.length < 4) {
    issues.push("at least 4 characters");
  }
  if (val.length > 40) {
    issues.push("less then 40 characters");
  }
  if (/\s/.test(val)) {
    issues.push("no whitespace");
  }
  if (!/[A-Z]/.test(val)) {
    issues.push("at least one uppercase letter");
  }
  if (!/\d/.test(val)) {
    issues.push("at least one digit");
  }
  if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(val)) {
    issues.push("at least one special character");
  }
  if (!/[a-z]/.test(val)) {
    issues.push("at least one lowercase letter");
  }
  if (issues.length) {
    ctx.addIssue({
      code: ZodIssueCode.custom,
      message: `Password must contain ${issues.join(", ")}.`,
    });
  }
  return false;
});

export { passwordSchema, nameSchema };
