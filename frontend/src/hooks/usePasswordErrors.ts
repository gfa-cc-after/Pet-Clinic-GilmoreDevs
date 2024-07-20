
/// Since  we should use the same password validation logic in multiple places,
/// we can create a custom hook to encapsulate this logic.

import { useEffect, useState } from "react";

const usePasswordValidator = (password: string) => {

    const [passwordErrors, setPasswordErrors] = useState<string[]>([]);

    const validatePassword = (password: string) => {
        const errors: string[] = [];
        if (password.length < 8) errors.push("must be at least 8 characters long");
        if (/\s/.test(password)) errors.push("must not contain whitespace");
        if (!/\d/.test(password)) errors.push("must contain at least one digit");
        if (!/[A-Z]/.test(password)) errors.push("must contain at least one uppercase letter");
        if (!/[a-z]/.test(password)) errors.push("must contain at least one lowercase letter");
        return errors;
    };

    useEffect(() => {
        setPasswordErrors(validatePassword(password));
    }, [password]);

    const isValid = () => passwordErrors.length !== 0;

    return { passwordErrors, isValid }

}
export { usePasswordValidator }