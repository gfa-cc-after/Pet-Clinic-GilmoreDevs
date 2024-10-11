"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { useToast } from "@/hooks/use-toast";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";

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

const FormSchema = z
  .object({
    password: passwordSchema,
    confirmPassword: passwordSchema,
    email: z
      .string()
      .email({ message: "Email must be a valid email address." }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords must match.",
    path: ["confirmPassword"],
  });

const LoginForm = () => {
  const { toast } = useToast();
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      email: "",
      password: "",
      confirmPassword: "",
    },
  });

  function onSubmit(data: z.infer<typeof FormSchema>) {
    toast({
      title: "You submitted the following values:",
      description: (
        <pre className="mt-2 w-[340px] rounded-md bg-slate-950 p-4">
          <code className="text-white">{JSON.stringify(data, null, 2)}</code>
        </pre>
      ),
    });
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="w-2/3 space-y-6">
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input placeholder="your email" {...field} />
              </FormControl>
              <FormDescription>
                Please enter your email address.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Password</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormDescription>Please enter your password.</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="confirmPassword"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Password confirmation</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormDescription>
                Please enter your password again.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
};

export { LoginForm };
