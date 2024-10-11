"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { useToast } from "@/hooks/use-toast";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { MaskedPassword } from "./MaskedPassword";
import { login } from "@/httpClient";
import { passwordSchema } from "@/lib/utils/schemas";
import { Card, CardContent, CardFooter, CardHeader } from "./ui/card";
import { AxiosError } from "axios";
import type { ActiveTab } from "./AuthenticationTabs";
import type React from "react";

const FormSchema = z.object({
  password: passwordSchema,
  email: z.string().email({ message: "Email must be a valid email address." }),
});

type FormValues = z.infer<typeof FormSchema>;

type LoginFormProps = {
  setActiveTab: React.Dispatch<React.SetStateAction<ActiveTab>>;
};

const LoginForm = ({ setActiveTab }: LoginFormProps) => {
  const { toast } = useToast();
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  async function onSubmit(data: FormValues) {
    try {
      await login(data);
    } catch (error) {
      if (error instanceof AxiosError && error.response) {
        toast({
          variant: "destructive",
          title: "Error during logging in",
          description: (
            <p>{error.response.data?.error || "Unknown error, try again..."}</p>
          ),
        });
      }
    }
  }

  return (
    <Form {...form}>
      <Card>
        <form onSubmit={form.handleSubmit(onSubmit)}>
          <CardHeader className="flex flex-col items-center">
            <h2 className="text-xl font-semibold">Login</h2>
            <p className="text-sm text-slate-600">Login with your account.</p>
          </CardHeader>
          <CardContent>
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input placeholder="your email" {...field} />
                  </FormControl>
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
                    <MaskedPassword {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter className="flex flex-col">
            <Button type="submit" className="my-2 w-1/2">
              Login
            </Button>
            <p className="text-sm text-slate-600 my-4">
              {" "}
              - or if you have not -{" "}
            </p>
            <Button
              className="my-2"
              variant="link"
              onClick={() => setActiveTab("register")}
            >
              Register
            </Button>
          </CardFooter>
        </form>
      </Card>
    </Form>
  );
};

export { LoginForm };
