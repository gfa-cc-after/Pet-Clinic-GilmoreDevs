import { emailValidation } from "@/httpClient";
import { debounce } from "./debounce";
import type {
  EmailValidationRequest,
  EmailValidationResponse,
} from "../../httpClient";

const DEBOUNCE_TIME_MS = 2000;

const debouncedValidateEmail = debounce<
  EmailValidationRequest,
  EmailValidationResponse,
  typeof emailValidation
>(async (request: EmailValidationRequest) => {
  return await emailValidation(request);
}, DEBOUNCE_TIME_MS);

export { debouncedValidateEmail };
