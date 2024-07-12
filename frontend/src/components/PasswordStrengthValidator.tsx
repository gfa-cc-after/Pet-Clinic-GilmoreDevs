import React from 'react';

interface PasswordStrengthValidatorProps {
  password: string;
}

const validatePassword = (password: string) => {
  const errors: string[] = [];
  if (password.length < 8) errors.push("must be at least 8 characters long");
  if (/\s/.test(password)) errors.push("must not contain whitespace");
  if (!/\d/.test(password)) errors.push("must contain at least one digit");
  if (!/[A-Z]/.test(password)) errors.push("must contain at least one uppercase letter");
  if (!/[a-z]/.test(password)) errors.push("must contain at least one lowercase letter");
  return errors;
};

const PasswordStrengthValidator: React.FC<PasswordStrengthValidatorProps> = ({ password }) => {
  const errors = validatePassword(password);
  return (
    <ul style={{ color: 'red', fontWeight: 'bold' }}>
      {errors.map((error, index) => (
        <li aria-label={'passworderrors'} key={index}>{error}</li>
      ))}
    </ul>
  );
};

export { PasswordStrengthValidator };
