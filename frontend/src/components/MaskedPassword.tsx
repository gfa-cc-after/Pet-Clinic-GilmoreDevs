import { type ReactNode, useState } from "react";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import type { ControllerRenderProps } from "react-hook-form";

type MaskedInputVariants = "password" | "text";

type MaskedPasswordProps = {
  placeholder?: string;
  visible?: ReactNode;
  hidden?: ReactNode;
} & ControllerRenderProps;

const MaskedPassword = (
  {
    hidden = '🙈',
    visible = '👁',
    name,
    onBlur,
    onChange,
    ref,
    value,
    disabled,
    placeholder,
  }: MaskedPasswordProps) => {
  const [inputMask, setInputMask] = useState<MaskedInputVariants>("password");
  const togglePassword = () => setInputMask(mask => mask === "text" ? "password" : "text");

  return (
    <div className="flex w-full max-w-sm items-center space-x-2">
      <Input
        type={inputMask}
        placeholder={placeholder}
        name={name}
        onBlur={onBlur}
        onChange={onChange}
        ref={ref}
        value={value}
        disabled={disabled}
      />
      <Button variant="ghost" onClick={togglePassword}>{inputMask === "text" ? visible : hidden}</Button>
    </div>
  );
}

export { MaskedPassword };