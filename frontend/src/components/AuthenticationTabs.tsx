import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/ui/tabs"
import { RegisterForm } from "./RegisterForm"
import { LoginForm } from "./LoginForm"
import { useState } from "react";

export type ActiveTab = "login" | "register";

const AuthenticationTabs = () => {
  const [activeTab, setActiveTab] = useState<ActiveTab>("login");

  return (
    <Tabs defaultValue="login" value={activeTab} className="w-[400px]">
      <TabsList className="grid w-full grid-cols-2">
        <TabsTrigger value="login" onClick={(_) => setActiveTab("login")}>Login</TabsTrigger>
        <TabsTrigger value="register" onClick={(_) => setActiveTab("register")}>Register</TabsTrigger>
      </TabsList>
      <TabsContent value="login">
        <LoginForm setActiveTab={setActiveTab} />
      </TabsContent>
      <TabsContent value="register">
        <RegisterForm setActiveTab={setActiveTab} />
      </TabsContent>
    </Tabs>
  )
}
export { AuthenticationTabs }