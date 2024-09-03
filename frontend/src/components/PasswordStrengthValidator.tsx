import type { FC } from "react";
interface PasswordStrengthValidatorProps {
  password: string;
}

const validatePassword = (password: string) => {
  const errors: string[] = [];
  if (password.length < 8) {
    errors.push("must be at least 8 characters long");
  }
  if (/\s/.test(password)) {
    errors.push("must not contain whitespace");
  }
  if (!/\d/.test(password)) {
    errors.push("must contain at least one digit");
  }
  if (!/[A-Z]/.test(password)) {
    errors.push("must contain at least one uppercase letter");
  }
  if (!/[a-z]/.test(password)) {
    errors.push("must contain at least one lowercase letter");
  }
  return errors;
};

const PasswordStrengthValidator: FC<PasswordStrengthValidatorProps> = ({
  password,
}) => {
  const errors = validatePassword(password);
  return (
    <div style={{
      color: "purple",
      fontWeight: "normal",
      fontSize: "12px"
    }}>
      {errors.map((error, _index) => (
        <p aria-label={"passworderrors"} key={error}>
          {error}
        </p>
      ))}
    </div>
  );
};

export { PasswordStrengthValidator };
