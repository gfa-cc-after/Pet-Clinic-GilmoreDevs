
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
import { register } from "@/httpClient";
import { passwordSchema, nameSchema } from "@/lib/utils/schemas";
import { Card, CardContent, CardFooter, CardHeader } from "./ui/card";
import { AxiosError } from "axios";
import type { ActiveTab } from "./AuthenticationTabs";

const FormSchema = z
  .object({
    firstName: nameSchema,
    lastName: nameSchema,
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

type FormValues = z.infer<typeof FormSchema>;

type RegisterFormProps = {
  setActiveTab: React.Dispatch<React.SetStateAction<ActiveTab>>;
};

const RegisterForm = ({ setActiveTab }: RegisterFormProps) => {
  const { toast } = useToast();
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
  });

  async function onSubmit(data: FormValues) {
    try {
      await register(data);
    } catch (error) {
      if (error instanceof AxiosError && error.response) {
        toast({
          variant: "destructive",
          title: "Error during registration",
          description: (
            <p>{error.response.data?.error || "Unknown error"}</p>
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
            <h2 className="text-xl font-semibold">Registration</h2>
            <p className="text-sm text-slate-600">
              Make changes to your account here. Click 'Register' when you're done.
            </p>
          </CardHeader>
          <CardContent>
            <FormField
              control={form.control}
              name="firstName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>firstname</FormLabel>
                  <FormControl>
                    <Input placeholder="your firstname" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="lastName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>lastname</FormLabel>
                  <FormControl>
                    <Input placeholder="your lastname" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
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
                    <MaskedPassword {...field} ref={field.ref} />
                  </FormControl>
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
                    <MaskedPassword {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter className="flex flex-col">
            <Button type="submit" className="my-2 w-1/2">Register</Button>
            <p className="text-sm text-slate-600 my-4"> - or if you already have and account - </p>
            <Button className="my-2" variant="link" onClick={() => setActiveTab("login")}>Login</Button>
          </CardFooter>
        </form>
      </Card >
    </Form >
  );
};

export { RegisterForm };
